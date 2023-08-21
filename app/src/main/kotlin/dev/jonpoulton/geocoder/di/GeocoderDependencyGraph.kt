package dev.jonpoulton.geocoder.di

import android.os.Build
import androidx.lifecycle.ViewModel
import dagger.BindsInstance
import dagger.Component
import dev.jonpoulton.alakazam.core.IBuildConfig
import dev.jonpoulton.alakazam.tak.core.AppContext
import dev.jonpoulton.alakazam.tak.core.PluginContext
import dev.jonpoulton.alakazam.tak.di.AlakazamAtakModule
import dev.jonpoulton.alakazam.tak.di.AlakazamClockModule
import dev.jonpoulton.alakazam.tak.di.AlakazamCoroutineModule
import dev.jonpoulton.alakazam.tak.di.AlakazamPreferencesModule
import dev.jonpoulton.alakazam.tak.di.AlakazamToasterModule
import dev.jonpoulton.alakazam.tak.di.DaggerInjector
import dev.jonpoulton.geocoder.core.GeocoderBuildConfig
import dev.jonpoulton.geocoder.http.di.HttpModule
import dev.jonpoulton.geocoder.mapquest.di.MapQuestModule
import dev.jonpoulton.geocoder.plugin.BuildConfig
import dev.jonpoulton.geocoder.plugin.R
import dev.jonpoulton.geocoder.positionstack.di.PositionStackModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AlakazamAtakModule::class,
    AlakazamClockModule::class,
    AlakazamCoroutineModule::class,
    AlakazamPreferencesModule::class,
    AlakazamToasterModule::class,
    AtakModule::class,
    HttpModule::class,
    MapQuestModule::class,
    PositionStackModule::class,
    SystemServiceModule::class,
  ],
)
interface GeocoderDependencyGraph : DaggerInjector {
  override fun vmFactory(): GeocoderViewModelFactory

  @Component.Factory
  interface Factory {
    fun withInstances(
      @BindsInstance pluginContext: PluginContext,
      @BindsInstance appContext: AppContext,
      @BindsInstance buildConfig: IBuildConfig,
      @BindsInstance geocoderBuildConfig: GeocoderBuildConfig,
    ): GeocoderDependencyGraph
  }

  companion object {
    fun init(pluginContext: PluginContext, appContext: AppContext) {
      val buildConfig = object : GeocoderBuildConfig {
        override val debug = BuildConfig.DEBUG
        override val applicationId = BuildConfig.APPLICATION_ID
        override val buildType = BuildConfig.BUILD_TYPE
        override val versionCode = BuildConfig.VERSION_CODE
        override val versionName = BuildConfig.VERSION_NAME
        override val buildTime = BuildConfig.BUILD_TIME
        override val gitId = BuildConfig.GIT_HASH
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

      NullableInstance = DaggerGeocoderDependencyGraph.factory()
        .withInstances(pluginContext, appContext, buildConfig, buildConfig)
    }
  }
}

private var NullableInstance: GeocoderDependencyGraph? = null

val DependencyGraph: GeocoderDependencyGraph by lazy { NullableInstance ?: error("Null component instance") }

inline fun <reified VM : ViewModel> viewModels(): Lazy<VM> = lazy { DependencyGraph.vmFactory().create(VM::class.java) }
