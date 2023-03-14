package dev.jonpoulton.geocoder.mapquest.model

@JvmInline
value class MapQuestApiKey(private val apiKey: String) {
  override fun toString(): String = apiKey
}
