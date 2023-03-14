package dev.jonpoulton.geocoder.positionstack.model

import dev.jonpoulton.geocoder.geocoding.Coordinates
import dev.jonpoulton.geocoder.positionstack.serializer.ForwardGeocodingResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(ForwardGeocodingResponseSerializer::class)
internal sealed class ForwardGeocodingResponse {
  @Serializable
  data class Error(
    @SerialName("error") val error: GeocodingError,
  ) : ForwardGeocodingResponse()

  @Serializable
  data class Success(
    @SerialName("data") val data: List<Coordinates>,
  ) : ForwardGeocodingResponse()
}
