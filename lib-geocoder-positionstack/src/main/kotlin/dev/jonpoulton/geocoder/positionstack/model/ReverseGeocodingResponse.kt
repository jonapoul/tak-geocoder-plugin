package dev.jonpoulton.geocoder.positionstack.model

import dev.jonpoulton.geocoder.positionstack.serializer.ReverseGeocodingResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(ReverseGeocodingResponseSerializer::class)
internal sealed class ReverseGeocodingResponse {
  @Serializable
  data class Error(
    @SerialName("error") val error: GeocodingError,
  ) : ReverseGeocodingResponse()

  @Serializable
  data class Success(
    @SerialName("data") val data: List<ReverseSuccessData>,
  ) : ReverseGeocodingResponse()
}

@Serializable
internal data class ReverseSuccessData(
  @SerialName("name") val name: String?,
  @SerialName("number") val number: String?,
  @SerialName("postal_code") val postalCode: String?,
  @SerialName("street") val street: String?,
  @SerialName("region") val region: String?,
  @SerialName("region_code") val regionCode: String?,
  @SerialName("county") val county: String?,
  @SerialName("locality") val locality: String?,
  @SerialName("administrative_area") val administrativeArea: String?,
  @SerialName("neighbourhood") val neighbourhood: String?,
  @SerialName("country") val country: String?,
  @SerialName("country_code") val countryCode: String?,
  @SerialName("continent") val continent: String?,
  @SerialName("label") val label: String?,
  // plus other unused fields
)

internal fun ReverseSuccessData.lines(): List<String> =
  listOfNotNull(
    name,
    neighbourhood,
    locality,
    county,
    postalCode,
    country,
    continent,
  )
    .map { it.trim() }
    .filter { it.isNotBlank() }
    .distinct() // remove duplicate lines
