package dev.jonpoulton.geocoder.geocoding

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject

abstract class BasicResponseSerializer<T : Any> : KSerializer<T> {
  @Suppress("DeprecatedCallableAddReplaceWith")
  @Deprecated(message = "Serialization is not supported for this type", level = DeprecationLevel.ERROR)
  override fun serialize(encoder: Encoder, value: T) {
    throw SerializationException(
      "Serialization is not supported, this class is only supposed to be deserialized! value=$value",
    )
  }

  final override fun deserialize(decoder: Decoder): T {
    require(decoder is JsonDecoder)
    val json = decoder.json
    val jsonObject = decoder.decodeJsonElement()
    require(jsonObject is JsonObject)
    return decodeJsonObject(json, jsonObject)
  }

  protected abstract fun decodeJsonObject(json: Json, jsonObject: JsonObject): T
}
