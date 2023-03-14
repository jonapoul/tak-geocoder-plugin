package dev.jonpoulton.geocoder.positionstack

import android.location.Address
import android.net.ConnectivityManager
import com.atakmap.coremap.maps.coords.GeoBounds
import com.atakmap.coremap.maps.coords.GeoPoint
import dev.jonpoulton.geocoder.core.PluginContext
import dev.jonpoulton.geocoder.core.isNetworkAvailable
import dev.jonpoulton.geocoder.core.runBlockingOrNull
import dev.jonpoulton.geocoder.di.POSITION_STACK_API_URL
import dev.jonpoulton.geocoder.geocoding.Coordinates
import dev.jonpoulton.geocoder.geocoding.CustomHttpGeocoder
import dev.jonpoulton.geocoder.positionstack.api.PositionStackApi
import dev.jonpoulton.geocoder.positionstack.model.ForwardGeocodingResponse
import dev.jonpoulton.geocoder.positionstack.model.PositionStackApiKey
import dev.jonpoulton.geocoder.positionstack.model.ReverseGeocodingResponse
import dev.jonpoulton.geocoder.positionstack.model.lines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.Locale

internal class PositionStackGeocoder(
  private val pluginContext: PluginContext,
  private val prefs: PositionStackPreferences,
  private val api: PositionStackApi,
  private val connectivityManager: ConnectivityManager,
) : CustomHttpGeocoder {
  override fun getUniqueIdentifier(): String = "position-stack"

  override fun getTitle(): String = pluginContext.getString(R.string.positionstack_title)

  override fun getDescription(): String = pluginContext.getString(R.string.positionstack_description)

  override val apiUrl = POSITION_STACK_API_URL

  override fun getCurrentApiKey(): String? = prefs.apiKey.get()?.toString()

  override fun apiKeyFlow(): Flow<String?> = prefs.apiKey.asFlow().map { it?.toString() }

  override fun getLocation(point: GeoPoint): List<Address> {
    val key = apiKeyOrNull() ?: return emptyList()
    val coordinates = Coordinates(point.latitude, point.longitude)
    val response = runBlockingOrNull { api.reverseGeocoding(key, coordinates) }
    Timber.i("Response = $response")

    return when (response) {
      is ReverseGeocodingResponse.Error, null -> emptyList()
      is ReverseGeocodingResponse.Success -> {
        response.data.map { item ->
          Address(Locale.getDefault()).apply {
            latitude = point.latitude
            longitude = point.longitude
            locality = item.locality
            countryCode = item.countryCode
            countryName = item.country
            item.lines().forEachIndexed(::setAddressLine)
          }
        }
      }
    }
  }

  override fun getLocation(address: String, bounds: GeoBounds): List<Address> {
    val key = apiKeyOrNull() ?: return emptyList()
    val response = runBlockingOrNull { api.forwardGeocoding(key, address) }
    Timber.i("Response = $response")

    return when (response) {
      is ForwardGeocodingResponse.Error, null -> emptyList()
      is ForwardGeocodingResponse.Success -> {
        response.data.map { item ->
          Address(Locale.getDefault()).apply {
            latitude = item.latitude
            longitude = item.longitude
          }
        }
      }
    }
  }

  override fun isAvailable(): Boolean = connectivityManager.isNetworkAvailable()

  private fun apiKeyOrNull(): PositionStackApiKey? {
    val key = prefs.apiKey.get()
    return if (key == null) {
      Timber.w("No API key set")
      null
    } else {
      key
    }
  }
}
