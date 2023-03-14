package dev.jonpoulton.geocoder.mapquest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ForwardGeocodingRequest(
  @SerialName("location") val location: String,
)
