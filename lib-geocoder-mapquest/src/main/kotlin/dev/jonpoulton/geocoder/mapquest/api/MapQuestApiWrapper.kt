package dev.jonpoulton.geocoder.mapquest.api

import dev.jonpoulton.geocoder.geocoding.runBlockingOrNull
import dev.jonpoulton.geocoder.http.LenientJson
import dev.jonpoulton.geocoder.mapquest.model.ForwardGeocodingRequest
import dev.jonpoulton.geocoder.mapquest.model.ForwardGeocodingResponse
import dev.jonpoulton.geocoder.mapquest.model.MapQuestApiKey
import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingRequest
import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingResponse

/**
 * Necessary because the Retrofit adapters for Kotlinx.serialization require reflection at runtime, which ATAK doesn't
 * allow us to do. So we need to encode bodies as JSON string before passing them into the API methods, and decode
 * responses from JSON strings.
 */
internal class MapQuestApiWrapper(private val api: MapQuestApi) {
  fun reverseGeocoding(
    apiKey: MapQuestApiKey,
    body: ReverseGeocodingRequest,
  ): ReverseGeocodingResponse? {
    val bodyJson = LenientJson.encodeToString(ReverseGeocodingRequest.serializer(), body)
    return runBlockingOrNull {
      val responseJson = api.reverseGeocoding(apiKey, bodyJson)
      LenientJson.decodeFromString(ReverseGeocodingResponse.serializer(), responseJson)
    }
  }

  fun forwardGeocoding(
    apiKey: MapQuestApiKey,
    body: ForwardGeocodingRequest,
  ): ForwardGeocodingResponse? {
    val bodyJson = LenientJson.encodeToString(ForwardGeocodingRequest.serializer(), body)
    return runBlockingOrNull {
      val responseJson = api.forwardGeocoding(apiKey, bodyJson)
      LenientJson.decodeFromString(ForwardGeocodingResponse.serializer(), responseJson)
    }
  }
}
