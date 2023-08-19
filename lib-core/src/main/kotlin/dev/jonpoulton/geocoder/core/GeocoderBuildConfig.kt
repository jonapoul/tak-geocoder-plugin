package dev.jonpoulton.geocoder.core

import dev.jonpoulton.alakazam.core.IBuildConfig

interface GeocoderBuildConfig : IBuildConfig {
  val buildType: String
  val w3wApiKey: String?
  val positionStackApiKey: String?
  val mapQuestApiKey: String?
}
