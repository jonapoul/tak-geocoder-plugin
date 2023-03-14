package dev.jonpoulton.geocoder.mapquest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MapQuestCoordinates(
  @SerialName("lat") val latitude: Double,
  @SerialName("lng") val longitude: Double,
)
