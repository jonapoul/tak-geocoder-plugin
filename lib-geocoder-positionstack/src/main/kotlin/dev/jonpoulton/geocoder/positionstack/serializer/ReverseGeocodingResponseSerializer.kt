package dev.jonpoulton.geocoder.positionstack.serializer

import dev.jonpoulton.geocoder.geocoding.BasicResponseSerializer
import dev.jonpoulton.geocoder.positionstack.model.ReverseGeocodingResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

internal object ReverseGeocodingResponseSerializer : BasicResponseSerializer<ReverseGeocodingResponse>() {
  override val descriptor = buildClassSerialDescriptor(serialName = "ReverseGeocodingResponse")

  override fun decodeJsonObject(json: Json, jsonObject: JsonObject): ReverseGeocodingResponse {
    return when {
      jsonObject.keys.contains("error") ->
        json.decodeFromJsonElement(ReverseGeocodingResponse.Error.serializer(), jsonObject)

      jsonObject.keys.contains("data") ->
        json.decodeFromJsonElement(ReverseGeocodingResponse.Success.serializer(), jsonObject)

      else -> throw SerializationException("Failed to deserialize response from $jsonObject")
    }
  }
}
