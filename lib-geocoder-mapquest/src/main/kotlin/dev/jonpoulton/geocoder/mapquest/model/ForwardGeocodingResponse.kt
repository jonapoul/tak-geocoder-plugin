package dev.jonpoulton.geocoder.mapquest.model

import dev.jonpoulton.geocoder.geocoding.Coordinates
import dev.jonpoulton.geocoder.mapquest.serializer.ForwardGeocodingResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(ForwardGeocodingResponseSerializer::class)
internal sealed class ForwardGeocodingResponse {
  data class Error(
    val statusCode: Int,
    val messages: List<String>,
  ) : ForwardGeocodingResponse()

  data class Success(
    val results: List<Coordinates>,
  ) : ForwardGeocodingResponse()
}

@Serializable
internal data class ForwardSuccessItem(
  @SerialName("locations") val locations: List<ForwardSuccessLocation>,
  // plus other rubbish
)

@Serializable
internal data class ForwardSuccessLocation(
  @SerialName("latLng") val coordinates: MapQuestCoordinates,
  // plus other rubbish
)
