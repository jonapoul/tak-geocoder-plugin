package dev.jonpoulton.geocoder.positionstack.api

import dev.jonpoulton.geocoder.core.runBlockingOrNull
import dev.jonpoulton.geocoder.geocoding.Coordinates
import dev.jonpoulton.geocoder.http.LenientJson
import dev.jonpoulton.geocoder.positionstack.model.ForwardGeocodingResponse
import dev.jonpoulton.geocoder.positionstack.model.PositionStackApiKey
import dev.jonpoulton.geocoder.positionstack.model.ReverseGeocodingResponse

internal class PositionStackApiWrapper(private val api: PositionStackApi) {
  fun reverseGeocoding(apiKey: PositionStackApiKey, coordinates: Coordinates): ReverseGeocodingResponse? {
    return runBlockingOrNull {
      val responseJson = api.reverseGeocoding(apiKey, coordinates)
      LenientJson.decodeFromString(ReverseGeocodingResponse.serializer(), responseJson)
    }
  }

  fun forwardGeocoding(apiKey: PositionStackApiKey, address: String): ForwardGeocodingResponse? {
    return runBlockingOrNull {
      val responseJson = api.forwardGeocoding(apiKey, address)
      LenientJson.decodeFromString(ForwardGeocodingResponse.serializer(), responseJson)
    }
  }
}
