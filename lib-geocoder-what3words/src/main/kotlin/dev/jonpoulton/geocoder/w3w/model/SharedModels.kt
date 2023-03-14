package dev.jonpoulton.geocoder.w3w.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SquareModel(
  @SerialName("southwest") val southWest: CoordinatesModel,
  @SerialName("northeast") val northEast: CoordinatesModel,
)

@Serializable
internal data class CoordinatesModel(
  @SerialName("lat") val latitude: Double,
  @SerialName("lng") val longitude: Double,
)
