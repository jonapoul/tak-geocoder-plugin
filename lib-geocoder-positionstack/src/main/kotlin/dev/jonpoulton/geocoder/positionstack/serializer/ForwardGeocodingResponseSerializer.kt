package dev.jonpoulton.geocoder.positionstack.serializer

import dev.jonpoulton.geocoder.geocoding.BasicResponseSerializer
import dev.jonpoulton.geocoder.positionstack.model.ForwardGeocodingResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

internal object ForwardGeocodingResponseSerializer : BasicResponseSerializer<ForwardGeocodingResponse>() {
  override val descriptor = buildClassSerialDescriptor(serialName = "ForwardGeocodingResponse")

  override fun decodeJsonObject(json: Json, jsonObject: JsonObject): ForwardGeocodingResponse {
    return when {
      jsonObject.keys.contains("error") ->
        json.decodeFromJsonElement(ForwardGeocodingResponse.Error.serializer(), jsonObject)

      jsonObject.keys.contains("data") ->
        json.decodeFromJsonElement(ForwardGeocodingResponse.Success.serializer(), jsonObject)

      else -> throw SerializationException("Failed to deserialize response from $jsonObject")
    }
  }
}
