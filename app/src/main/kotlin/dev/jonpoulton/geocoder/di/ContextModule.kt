package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.core.AppContext
import dev.jonpoulton.geocoder.core.PluginContext
import org.koin.dsl.module

internal fun contextModule(appContext: AppContext, pluginContext: PluginContext) = module {
  single { appContext }
  single { pluginContext }
}
