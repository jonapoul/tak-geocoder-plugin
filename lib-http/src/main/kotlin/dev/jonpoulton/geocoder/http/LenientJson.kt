package dev.jonpoulton.geocoder.http

import kotlinx.serialization.json.Json

val LenientJson = Json {
  isLenient = true
  ignoreUnknownKeys = true
  explicitNulls = false
}
