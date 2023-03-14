package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.geocoding.LocationMonitor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val geocodingModule = module {
  single { LocationMonitor(get(), get(), get(), get(named(KoinDispatchers.IO)), get()) }
}
