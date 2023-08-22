package dev.jonpoulton.geocoder.di

import dagger.Module
import dagger.Provides
import dev.jonpoulton.alakazam.core.DefaultDispatcher
import dev.jonpoulton.alakazam.core.IODispatcher
import dev.jonpoulton.alakazam.core.MainDispatcher
import dev.jonpoulton.alakazam.core.UnconfinedDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
class CoroutineModule {
  @Provides
  @Singleton
  fun scope(): CoroutineScope = CoroutineScope(SupervisorJob())

  @Provides
  fun main(): MainDispatcher = MainDispatcher(Dispatchers.Main)

  @Provides
  fun io(): IODispatcher = IODispatcher(Dispatchers.IO)

  @Provides
  fun default(): DefaultDispatcher = DefaultDispatcher(Dispatchers.Default)

  @Provides
  fun unconfined(): UnconfinedDispatcher = UnconfinedDispatcher(Dispatchers.Unconfined)
}
