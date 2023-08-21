package dev.jonpoulton.geocoder.geocoding

import android.location.Address
import com.atakmap.android.maps.MapView
import com.atakmap.android.user.geocode.GeocodeManager
import com.atakmap.android.user.geocode.GeocodeManager.Geocoder
import com.atakmap.android.user.geocode.GeocodeManager.Geocoder2
import com.atakmap.coremap.maps.coords.GeoPoint
import dev.jonpoulton.alakazam.core.IODispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class LocationMonitor(
  protected val mapView: MapView,
  protected val geocodeManager: GeocodeManager,
  protected val scope: CoroutineScope,
  protected val io: IODispatcher,
) {
  private val mutableState = MutableStateFlow<GeocodedState>(value = GeocodedState.NoPositionFound)

  /* Combining here to force the state flow to emit a new value every time the includeTag setting updates */
  val geocodedState: Flow<GeocodedState> = mutableState.asStateFlow()

  private var loopJob: Job? = null
  private var workingJob: Job? = null
  private val tag = this::class.java.simpleName

  protected abstract fun getPoint(): GeoPoint?
  protected abstract fun refreshPeriodMs(): Long
  protected open fun hasAlreadyBeenGeocoded(point: GeoPoint?): Boolean = false
  protected open fun geocodingEnabled(): Boolean = true

  fun startGeocoding() {
    Timber.v("$tag - start")
    loopJob?.cancel()
    loopJob = scope.launch {
      while (true) {
        val point = getPoint()
        val geocoder = geocodeManager.selectedGeocoder
        when {
          geocoder == null -> {
            Timber.e("$tag - No geocoder implementation on this device!")
            mutableState.value = GeocodedState.NoGeocoders
          }

          !geocodingEnabled() -> {
            Timber.e("$tag - Geocoding disabled")
            mutableState.value = GeocodedState.HideWidget
          }

          hasAlreadyBeenGeocoded(point) -> {
            Timber.e("$tag - Point has already been geocoded, not running again: $point")
          }

          else -> {
            launchWorkingJob(geocoder)
            mutableState.value = getStateFromPoint(point, geocoder)
          }
        }
        delay(refreshPeriodMs())
      }
    }
  }

  fun stopGeocoding() {
    Timber.v("$tag - stop")
    loopJob?.cancel()
    workingJob?.cancel()
    loopJob = null
    workingJob = null
  }

  @Suppress("ReturnCount")
  private suspend fun getStateFromPoint(point: GeoPoint?, geocoder: Geocoder): GeocodedState {
    if (point == null) {
      workingJob?.cancel()
      return GeocodedState.NoPositionFound
    }

    if (!geocoder.checkAvailability()) {
      Timber.w("$tag - Geocoder $geocoder is not available")
      return GeocodedState.NotAvailable(geocoder)
    }

    val addresses = withContext(io) { geocoder.getLocation(point) } // this blocks the thread
    workingJob?.cancel() // address has been fetched, so we're not blocking the thread any more

    if (addresses.isNullOrEmpty()) {
      return GeocodedState.Failed("No geocoding results", geocoder)
    }

    val address = addresses.first()
    val addressString = address.getExistingAddressLinesOrNull() ?: address.getAddressFromComponentsOrNull()

    Timber.v("$tag - addressString = ${addressString?.split(NEW_LINE)}")
    return if (addressString == null) {
      GeocodedState.Failed("Couldn't parse address", geocoder)
    } else {
      GeocodedState.Success(addressString, geocoder)
    }
  }

  private fun launchWorkingJob(geocoder: Geocoder) {
    workingJob?.cancel()
    val delay = if (mutableState.value is GeocodedState.Success) 3_000L else 500L
    workingJob = scope.launch {
      delay(delay)
      mutableState.value = GeocodedState.Working(geocoder)
    }
  }

  private fun Address.getExistingAddressLinesOrNull(): String? {
    val lines = arrayListOf<String>()
    var index = 0
    while (true) {
      val line = getAddressLine(index++) ?: break
      lines.add(line)
    }
    return if (lines.isEmpty()) {
      null
    } else {
      lines.joinToString()
        .split(COMMA)
        .joinToString(NEW_LINE)
    }
  }

  private fun Address.getAddressFromComponentsOrNull(): String? {
    val components = listOfNotNull(
      featureName ?: subThoroughfare,
      subThoroughfare,
      thoroughfare,
      locality,
      subAdminArea,
      adminArea,
      postalCode,
      countryName ?: countryCode,
    )
    return components.joinToString().ifBlank { null }
  }

  private fun Geocoder.checkAvailability(): Boolean = when {
    !testServiceAvailable() -> false
    this !is Geocoder2 -> false
    else -> isAvailable // from the Geocoder2 interface
  }

  private companion object {
    const val NEW_LINE = "\n"
    const val COMMA = ","
  }
}
