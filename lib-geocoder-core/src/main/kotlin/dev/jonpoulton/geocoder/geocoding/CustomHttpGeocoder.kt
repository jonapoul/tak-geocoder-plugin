package dev.jonpoulton.geocoder.geocoding

import com.atakmap.android.user.geocode.GeocodeManager
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Empty interface to identify our custom implementations
 */
interface CustomHttpGeocoder : GeocodeManager.Geocoder2 {
  val apiUrl: String

  fun getCurrentApiKey(): String?
  fun apiKeyFlow(): Flow<String?>

  /**
   * Copied from [GeocodeManager.NominatimGeocoder.testServiceAvailable]. Just tries to open a connection to the
   * remote endpoint, doesn't try to request any data or parse any responses.
   */
  override fun testServiceAvailable(): Boolean {
    return try {
      val connection = URL(apiUrl).openConnection() as HttpURLConnection
      connection.setRequestProperty("User-Agent", "TAK")
      connection.connectTimeout = 10000
      connection.readTimeout = 10000
      connection.connect()
      true
    } catch (e: IOException) {
      Timber.w(e)
      false
    }
  }
}
