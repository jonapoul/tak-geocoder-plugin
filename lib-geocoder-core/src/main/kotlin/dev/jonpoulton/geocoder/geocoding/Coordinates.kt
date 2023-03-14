package dev.jonpoulton.geocoder.geocoding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
  @SerialName("latitude") val latitude: Double,
  @SerialName("longitude") val longitude: Double,
) {
  override fun toString(): String = "$latitude,$longitude"
}
