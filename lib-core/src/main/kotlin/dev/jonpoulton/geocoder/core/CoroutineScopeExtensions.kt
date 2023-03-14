package dev.jonpoulton.geocoder.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> CoroutineScope.collectFlow(flow: Flow<T>, call: (T) -> Unit): Job = launch {
  flow.collect { call.invoke(it) }
}
