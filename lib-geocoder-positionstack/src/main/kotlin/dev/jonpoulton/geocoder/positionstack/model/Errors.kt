package dev.jonpoulton.geocoder.positionstack.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GeocodingError(
  @SerialName("code") val code: String,
  @SerialName("message") val message: String,
  @SerialName("context") val context: ErrorContext?,
)

@Serializable
internal data class ErrorContext(
  @SerialName("query") val query: ErrorQuery,
)

@Serializable
internal data class ErrorQuery(
  @SerialName("type") val type: String,
  @SerialName("message") val message: String,
)
