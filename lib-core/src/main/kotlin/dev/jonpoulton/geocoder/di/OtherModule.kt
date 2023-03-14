package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.core.Toaster
import org.koin.core.qualifier.named
import org.koin.dsl.module

val otherModule = module {
  factory { Toaster(get(), get(), get(named(KoinDispatchers.MAIN))) }
}
