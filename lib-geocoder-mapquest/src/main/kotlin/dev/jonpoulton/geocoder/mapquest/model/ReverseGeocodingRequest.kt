package dev.jonpoulton.geocoder.mapquest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ReverseGeocodingRequest(
  @SerialName("location") val location: ReverseGeocodingLocation,
)

@Serializable
internal data class ReverseGeocodingLocation(
  @SerialName("latLng") val coordinates: MapQuestCoordinates,
)
