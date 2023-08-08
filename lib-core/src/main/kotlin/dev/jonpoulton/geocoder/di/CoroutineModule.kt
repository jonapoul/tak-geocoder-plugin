package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.core.DefaultDispatcher
import dev.jonpoulton.geocoder.core.IODispatcher
import dev.jonpoulton.geocoder.core.MainDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

@Suppress("InjectDispatcher")
val coroutineModule = module {
  /* Application scope */
  single { CoroutineScope(SupervisorJob()) }

  /* Dispatchers */
  single { MainDispatcher(Dispatchers.Main) }
  single { IODispatcher(Dispatchers.IO) }
  single { DefaultDispatcher(Dispatchers.Default) }
}
