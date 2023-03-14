package dev.jonpoulton.geocoder.mapquest.model

import dev.jonpoulton.geocoder.mapquest.serializer.ReverseGeocodingResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(ReverseGeocodingResponseSerializer::class)
internal sealed class ReverseGeocodingResponse {
  data class Error(
    val statusCode: Int,
    val messages: List<String>,
  ) : ReverseGeocodingResponse()

  data class Success(
    val results: List<ReverseSuccessLocation>,
  ) : ReverseGeocodingResponse()
}

@Serializable
internal data class ReverseSuccessItem(
  @SerialName("locations") val locations: List<ReverseSuccessLocation>,
  // plus other rubbish
)

@Serializable
internal data class ReverseSuccessLocation(
  @SerialName("street") val street: String,
  @SerialName("adminArea6") val neighbourhood: String,
  @SerialName("adminArea5") val city: String,
  @SerialName("adminArea4") val county: String,
  @SerialName("adminArea3") val state: String,
  @SerialName("adminArea1") val country: String,
  @SerialName("postalCode") val postalCode: String,
  @SerialName("latLng") val coordinates: MapQuestCoordinates,
  // plus other rubbish
)

internal fun ReverseSuccessLocation.lines(): List<String> =
  listOfNotNull(
    street,
    neighbourhood,
    city,
    county,
    state,
    country,
    postalCode,
  )
    .map { it.trim() }
    .filter { it.isNotBlank() }
    .distinct() // remove duplicate lines
