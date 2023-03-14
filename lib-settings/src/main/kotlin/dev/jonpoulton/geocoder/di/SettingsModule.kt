package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.settings.PluginPreferences
import org.koin.dsl.module

val settingsModule = module {
  single { PluginPreferences(get()) }
}
