package dev.jonpoulton.geocoder.mapquest.serializer

import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingResponse
import dev.jonpoulton.geocoder.mapquest.model.ReverseSuccessItem
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray

internal object ReverseGeocodingResponseSerializer : MapQuestSerializer<ReverseGeocodingResponse>() {
  override val descriptor = buildClassSerialDescriptor(serialName = "ReverseGeocodingResponse")

  override fun errorTypeConstructor() = ReverseGeocodingResponse::Error

  override fun handleResultsArray(json: Json, jsonArray: JsonArray): ReverseGeocodingResponse {
    val serializer = ReverseSuccessItem.serializer()
    val locations = jsonArray
      .map { json.decodeFromJsonElement(serializer, it) }
      .map { item -> item.locations }
      .flatten()
    return ReverseGeocodingResponse.Success(locations)
  }
}
