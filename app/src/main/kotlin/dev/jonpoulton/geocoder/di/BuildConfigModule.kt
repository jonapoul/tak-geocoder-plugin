package dev.jonpoulton.geocoder.di

import android.os.Build
import dagger.Module
import dagger.Provides
import dev.jonpoulton.alakazam.core.IBuildConfig
import dev.jonpoulton.alakazam.tak.core.PluginContext
import dev.jonpoulton.geocoder.core.GeocoderBuildConfig
import dev.jonpoulton.geocoder.plugin.BuildConfig
import dev.jonpoulton.geocoder.plugin.R

@Module
internal class BuildConfigModule(private val pluginContext: PluginContext) {

  private val buildConfig = object : GeocoderBuildConfig {
    override val debug = BuildConfig.DEBUG
    override val applicationId = BuildConfig.APPLICATION_ID
    override val buildType = BuildConfig.BUILD_TYPE
    override val versionCode = BuildConfig.VERSION_CODE
    override val versionName = BuildConfig.VERSION_NAME
    override val buildTime = BuildConfig.BUILD_TIME
    override val gitId = TODO("Not yet implemented")
    override val manufacturer = Build.MANUFACTURER
    override val model = Build.MODEL
    override val os = Build.VERSION.SDK_INT
    override val platform = pluginContext.getString(R.string.plugin_name)
    override val repoName = "jonapoul/tak-geocoder-plugin"
    override val repoUrl = "https://github.com/$repoName"
    override val w3wApiKey = BuildConfig.W3W_API_KEY
    override val positionStackApiKey = BuildConfig.POSITIONSTACK_API_KEY
    override val mapQuestApiKey = BuildConfig.MAPQUEST_API_KEY
  }

  @Provides
  fun buildConfig(): IBuildConfig = buildConfig

  @Provides
  fun geocoderBuildConfig(): GeocoderBuildConfig = buildConfig
}
