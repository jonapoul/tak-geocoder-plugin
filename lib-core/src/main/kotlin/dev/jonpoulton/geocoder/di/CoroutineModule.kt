package dev.jonpoulton.geocoder.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineModule = module {
  /* Application scope */
  single { CoroutineScope(SupervisorJob()) }

  /* Dispatchers */
  single<CoroutineDispatcher>(named(KoinDispatchers.MAIN)) { Dispatchers.Main }
  single(named(KoinDispatchers.IO)) { Dispatchers.IO }
  single(named(KoinDispatchers.DEFAULT)) { Dispatchers.Default }
}

object KoinDispatchers {
  const val MAIN = "KoinDispatchers.MAIN"
  const val IO = "KoinDispatchers.IO"
  const val DEFAULT = "KoinDispatchers.DEFAULT"
}
