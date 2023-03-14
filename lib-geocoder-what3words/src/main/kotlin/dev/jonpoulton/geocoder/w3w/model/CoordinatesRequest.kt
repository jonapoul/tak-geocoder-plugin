package dev.jonpoulton.geocoder.w3w.model

internal data class CoordinatesRequest(
  val latitude: Double,
  val longitude: Double,
) {
  override fun toString(): String = "$latitude,$longitude"
}
