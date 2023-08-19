package dev.jonpoulton.geocoder.di

import dagger.BindsInstance
import dagger.Component
import dev.jonpoulton.alakazam.tak.core.AppContext
import dev.jonpoulton.alakazam.tak.core.PluginContext
import dev.jonpoulton.alakazam.tak.di.AlakazamAtakModule
import dev.jonpoulton.alakazam.tak.di.AlakazamClockModule
import dev.jonpoulton.alakazam.tak.di.AlakazamCoroutineModule
import dev.jonpoulton.alakazam.tak.di.AlakazamPreferencesModule
import dev.jonpoulton.alakazam.tak.di.AlakazamToasterModule
import dev.jonpoulton.alakazam.tak.di.DaggerInjector
import dev.jonpoulton.geocoder.mapquest.di.MapQuestModule
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
    BuildConfigModule::class,
    CoroutineScopeModule::class,
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
    fun withContexts(
      @BindsInstance pluginContext: PluginContext,
      @BindsInstance appContext: AppContext,
    ): GeocoderDependencyGraph
  }

  companion object {
    fun init(pluginContext: PluginContext, appContext: AppContext) {
      NullableInstance = DaggerGeocoderDependencyGraph.factory().withContexts(pluginContext, appContext)
    }
  }
}

private var NullableInstance: GeocoderDependencyGraph? = null

val DependencyGraph: GeocoderDependencyGraph by lazy { NullableInstance ?: error("Null component instance") }
