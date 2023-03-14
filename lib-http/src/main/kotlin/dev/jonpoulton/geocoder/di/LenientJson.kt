package dev.jonpoulton.geocoder.di

import kotlinx.serialization.json.Json

val LenientJson = Json {
  isLenient = true
  ignoreUnknownKeys = true
  explicitNulls = false
}
