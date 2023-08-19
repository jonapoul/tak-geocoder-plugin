package dev.jonpoulton.geocoder.mapquest.serializer

import dev.jonpoulton.geocoder.geocoding.BasicResponseSerializer
import dev.jonpoulton.geocoder.mapquest.model.ResponseInfo
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

internal abstract class MapQuestSerializer<T : Any> : BasicResponseSerializer<T>() {
  protected abstract fun handleResultsArray(json: Json, jsonArray: JsonArray): T
  protected abstract val errorTypeConstructor: (Int, List<String>) -> T

  final override fun decodeJsonObject(json: Json, jsonObject: JsonObject): T {
    val infoObject = jsonObject["info"] as? JsonObject ?: error("Required info block in $jsonObject")
    val info = json.decodeFromJsonElement(ResponseInfo.serializer(), infoObject)
    if (info.statusCode != 0) {
      return errorTypeConstructor(info.statusCode, info.messages)
    }

    val results = jsonObject["results"] as? JsonArray ?: error("Required results array in $jsonObject")
    return handleResultsArray(json, results)
  }
}
