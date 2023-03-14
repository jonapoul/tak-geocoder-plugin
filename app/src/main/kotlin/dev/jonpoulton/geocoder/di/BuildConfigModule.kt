package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.core.IBuildConfig
import dev.jonpoulton.geocoder.plugin.BuildConfig
import org.koin.dsl.module

private val PluginBuildConfig = object : IBuildConfig {
  override val isDebug = BuildConfig.DEBUG
  override val applicationId = BuildConfig.APPLICATION_ID
  override val buildType = BuildConfig.BUILD_TYPE
  override val flavour = BuildConfig.FLAVOR
  override val versionCode = BuildConfig.VERSION_CODE
  override val versionName = BuildConfig.VERSION_NAME
  override val buildTime = BuildConfig.BUILD_TIME
  override val w3wApiKey = BuildConfig.W3W_API_KEY
  override val positionStackApiKey = BuildConfig.POSITIONSTACK_API_KEY
  override val mapQuestApiKey = BuildConfig.MAPQUEST_API_KEY
}

internal val buildConfigModule = module {
  single<IBuildConfig> { PluginBuildConfig }
}
