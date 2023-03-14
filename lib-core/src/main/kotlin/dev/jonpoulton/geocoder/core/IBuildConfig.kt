package dev.jonpoulton.geocoder.core

import java.time.Instant

interface IBuildConfig {
  val isDebug: Boolean
  val applicationId: String
  val buildType: String
  val flavour: String
  val versionCode: Int
  val versionName: String
  val buildTime: Instant
  val w3wApiKey: String?
  val positionStackApiKey: String?
  val mapQuestApiKey: String?
}

/**
 * Don't use this in the actual code!
 */
object TestBuildConfig : IBuildConfig {
  override val isDebug = true
  override val applicationId = "HELLO WORLD"
  override val buildType = "HELLO WORLD"
  override val flavour = "HELLO WORLD"
  override val versionCode = 123
  override val versionName = "1.2.3"
  override val buildTime = Instant.now()!!
  override val w3wApiKey = "HELLO WORLD"
  override val positionStackApiKey = "HELLO WORLD"
  override val mapQuestApiKey = "HELLO WORLD"
}
