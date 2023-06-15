package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.settings.PluginPreferences
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsModule = module {
  singleOf(::PluginPreferences)
}
