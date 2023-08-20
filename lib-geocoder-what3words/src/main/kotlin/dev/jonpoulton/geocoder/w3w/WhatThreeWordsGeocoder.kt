package dev.jonpoulton.geocoder.w3w

import android.location.Address
import android.net.ConnectivityManager
import com.atakmap.coremap.maps.coords.GeoBounds
import com.atakmap.coremap.maps.coords.GeoPoint
import dev.jonpoulton.alakazam.tak.core.PluginContext
import dev.jonpoulton.geocoder.core.isNetworkAvailable
import dev.jonpoulton.geocoder.geocoding.CustomHttpGeocoder
import dev.jonpoulton.geocoder.w3w.W3W_API_URL
import dev.jonpoulton.geocoder.w3w.api.WhatThreeWordsApi
import dev.jonpoulton.geocoder.w3w.api.WhatThreeWordsApiWrapper
import dev.jonpoulton.geocoder.w3w.model.CoordinatesRequest
import dev.jonpoulton.geocoder.w3w.model.WhatThreeWordsApiKey
import dev.jonpoulton.geocoder.what3words.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import java.util.Locale

internal class WhatThreeWordsGeocoder @Inject constructor(
    private val pluginContext: PluginContext,
    private val prefs: WhatThreeWordsPreferences,
    private val api: WhatThreeWordsApi,
    private val connectivityManager: ConnectivityManager,
) : CustomHttpGeocoder {
  override fun getUniqueIdentifier(): String = "what-three-words"

  override fun getTitle(): String = pluginContext.getString(R.string.w3w_title)

  override fun getDescription(): String = pluginContext.getString(R.string.w3w_description)

  override val apiUrl = W3W_API_URL

  override fun getCurrentApiKey(): String? = prefs.apiKey.get()?.toString()

  override fun apiKeyFlow(): Flow<String?> = prefs.apiKey.asFlow().map { it?.toString() }

  override fun getLocation(point: GeoPoint): List<Address> {
    val key = apiKeyOrNull() ?: return emptyList()

    val coordinates = CoordinatesRequest(point.latitude, point.longitude)
    val response = WhatThreeWordsApiWrapper(api).reverseGeocoding(key, coordinates)
    Timber.i("Response = $response")

    return when (response) {
      null -> emptyList()
      else -> {
        val address = Address(Locale.getDefault()).apply {
          setAddressLine(0, response.words.toString())
        }
        listOf(address)
      }
    }
  }

  override fun getLocation(address: String, bounds: GeoBounds): List<Address> {
    Timber.e("Don't support reverse geocoding in W3W! address=$address bounds=$bounds")
    return emptyList()
  }

  override fun isAvailable(): Boolean = apiKeyOrNull() != null && connectivityManager.isNetworkAvailable()

  private fun apiKeyOrNull(): WhatThreeWordsApiKey? {
    val key = prefs.apiKey.get()
    return if (key == null) {
      Timber.w("No API key set")
      null
    } else {
      key
    }
  }
}
