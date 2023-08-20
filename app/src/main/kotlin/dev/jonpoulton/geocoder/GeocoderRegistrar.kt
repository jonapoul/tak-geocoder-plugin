package dev.jonpoulton.geocoder

import com.atakmap.android.user.geocode.GeocodeManager
import dev.jonpoulton.alakazam.core.collectFlow
import dev.jonpoulton.geocoder.geocoding.CustomHttpGeocoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocoderRegistrar @Inject constructor(
  private val scope: CoroutineScope,
  private val geocodeManager: GeocodeManager,
  private val geocoders: Set<@JvmSuppressWildcards CustomHttpGeocoder>,
) {
  private var job: Job? = null

  fun start() {
    /* This flow updates every time any API key changes */
    val flow = combine(geocoders.map { it.apiKeyFlow() }) { it }
    job?.cancel()
    job = scope.collectFlow(flow) { handleKeys() }
  }

  fun stop() {
    job?.cancel()
    job = null
    geocodeManager.allGeocoders
      .filterIsInstance<CustomHttpGeocoder>()
      .forEach(geocodeManager::unregisterGeocoder)
  }

  private fun handleKeys() {
    geocoders.forEach { geocoder ->
      Timber.d("unregister $geocoder")
      geocodeManager.unregisterGeocoder(geocoder)

      if (geocoder.getCurrentApiKey().isValidKey()) {
        Timber.d("register $geocoder")
        geocodeManager.registerGeocoder(geocoder)
      }
    }
  }

  private fun String?.isValidKey(): Boolean = !this.isNullOrBlank()
}
