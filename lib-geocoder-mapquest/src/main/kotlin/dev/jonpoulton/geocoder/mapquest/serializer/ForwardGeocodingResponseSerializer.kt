package dev.jonpoulton.geocoder.mapquest.serializer

import dev.jonpoulton.geocoder.geocoding.Coordinates
import dev.jonpoulton.geocoder.mapquest.model.ForwardGeocodingResponse
import dev.jonpoulton.geocoder.mapquest.model.ForwardSuccessItem
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray

internal object ForwardGeocodingResponseSerializer : MapQuestSerializer<ForwardGeocodingResponse>() {
  override val descriptor = buildClassSerialDescriptor(serialName = "ForwardGeocodingResponse")
  override val errorTypeConstructor = ForwardGeocodingResponse::Error

  override fun handleResultsArray(json: Json, jsonArray: JsonArray): ForwardGeocodingResponse {
    val serializer = ForwardSuccessItem.serializer()
    val coordinates = jsonArray
      .map { json.decodeFromJsonElement(serializer, it) }
      .map { item -> item.locations.map { it.coordinates } }
      .flatten()
      .map { Coordinates(it.latitude, it.longitude) }

    return ForwardGeocodingResponse.Success(coordinates)
  }
}
