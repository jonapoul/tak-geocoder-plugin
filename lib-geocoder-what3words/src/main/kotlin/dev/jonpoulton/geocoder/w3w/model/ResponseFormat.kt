package dev.jonpoulton.geocoder.w3w.model

internal enum class ResponseFormat(private val string: String) {
  Json(string = "json"),
  GeoJson(string = "geojson"),
  ;

  override fun toString(): String = string
}
