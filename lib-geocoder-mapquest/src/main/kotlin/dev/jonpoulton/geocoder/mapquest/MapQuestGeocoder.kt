package dev.jonpoulton.geocoder.mapquest

import android.location.Address
import android.net.ConnectivityManager
import com.atakmap.coremap.maps.coords.GeoBounds
import com.atakmap.coremap.maps.coords.GeoPoint
import dev.jonpoulton.alakazam.tak.core.PluginContext
import dev.jonpoulton.geocoder.core.isNetworkAvailable
import dev.jonpoulton.geocoder.geocoding.CustomHttpGeocoder
import dev.jonpoulton.geocoder.mapquest.api.MapQuestApi
import dev.jonpoulton.geocoder.mapquest.api.MapQuestApiWrapper
import dev.jonpoulton.geocoder.mapquest.model.ForwardGeocodingRequest
import dev.jonpoulton.geocoder.mapquest.model.ForwardGeocodingResponse
import dev.jonpoulton.geocoder.mapquest.model.MapQuestApiKey
import dev.jonpoulton.geocoder.mapquest.model.MapQuestCoordinates
import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingLocation
import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingRequest
import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingResponse
import dev.jonpoulton.geocoder.mapquest.model.lines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

/**
 * This is an alternative to the built-in MapQuest Geocoder implementation, which doesn't work as of ATAK 4.8.1.
 * Most likely because it hasn't been touched in ages and the MapQuest API endpoints have changed - because it only
 * returns failure responses whenever ATAK tries to ping it.
 *
 * Check [com.atakmap.android.user.geocode.GeocoderNominatim] for the faulty built-in implementation.
 */
internal class MapQuestGeocoder @Inject constructor(
  private val pluginContext: PluginContext,
  private val prefs: MapQuestPreferences,
  private val api: MapQuestApi,
  private val connectivityManager: ConnectivityManager,
) : CustomHttpGeocoder {
  override fun getUniqueIdentifier(): String = "mapquest-working"

  override fun getTitle(): String = pluginContext.getString(R.string.mapquest_title)

  override fun getDescription(): String = pluginContext.getString(R.string.mapquest_description)

  override val apiUrl = MAP_QUEST_API_URL

  override fun getCurrentApiKey(): String? = prefs.apiKey.get()?.toString()

  override fun apiKeyFlow(): Flow<String?> = prefs.apiKey.asFlow().map { it?.toString() }

  override fun getLocation(point: GeoPoint): List<Address> {
    val key = apiKeyOrNull() ?: return emptyList()
    val body = ReverseGeocodingRequest(
      location = ReverseGeocodingLocation(
        coordinates = MapQuestCoordinates(
          latitude = point.latitude,
          longitude = point.longitude,
        ),
      ),
    )
    val response = MapQuestApiWrapper(api).reverseGeocoding(key, body)
    Timber.i("Response = $response")

    return when (response) {
      is ReverseGeocodingResponse.Error, null -> emptyList()
      is ReverseGeocodingResponse.Success -> response.results.map { location ->
        Address(Locale.getDefault()).apply {
          latitude = location.coordinates.latitude
          longitude = location.coordinates.longitude
          countryName = location.country
          location.lines().forEachIndexed(::setAddressLine)
        }
      }
    }
  }

  override fun getLocation(address: String, bounds: GeoBounds): List<Address> {
    val key = apiKeyOrNull() ?: return emptyList()
    val body = ForwardGeocodingRequest(location = address)
    val response = MapQuestApiWrapper(api).forwardGeocoding(key, body)
    Timber.i("Response = $response")

    return when (response) {
      is ForwardGeocodingResponse.Error, null -> emptyList()
      is ForwardGeocodingResponse.Success -> {
        response.results.map { item ->
          Address(Locale.getDefault()).apply {
            latitude = item.latitude
            longitude = item.longitude
          }
        }
      }
    }
  }

  override fun isAvailable(): Boolean = connectivityManager.isNetworkAvailable()

  private fun apiKeyOrNull(): MapQuestApiKey? {
    val key = prefs.apiKey.get()
    return if (key == null) {
      Timber.w("No API key set")
      null
    } else {
      key
    }
  }
}
