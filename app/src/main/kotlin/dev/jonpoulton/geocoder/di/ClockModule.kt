package dev.jonpoulton.geocoder.di

import dagger.Module
import dagger.Provides
import dev.jonpoulton.alakazam.time.Clock
import dev.jonpoulton.alakazam.time.SystemClock

@Module
class ClockModule {
  @Provides
  fun clock(): Clock = SystemClock
}
