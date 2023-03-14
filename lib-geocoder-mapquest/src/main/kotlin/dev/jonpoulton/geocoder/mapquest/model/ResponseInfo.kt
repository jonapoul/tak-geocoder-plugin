package dev.jonpoulton.geocoder.mapquest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ResponseInfo(
  @SerialName("statuscode") val statusCode: Int,
  @SerialName("messages") val messages: List<String>,
)
