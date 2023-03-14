package dev.jonpoulton.geocoder.positionstack.model

@JvmInline
value class PositionStackApiKey(private val apiKey: String) {
  override fun toString(): String = apiKey
}
