package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.GeocoderRegistrar
import org.koin.dsl.module

val pluginModule = module {
  single { GeocoderRegistrar(get(), get(), getAll()) }
}
