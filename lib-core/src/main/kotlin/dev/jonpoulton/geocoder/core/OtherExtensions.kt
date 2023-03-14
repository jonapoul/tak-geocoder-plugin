package dev.jonpoulton.geocoder.core

import kotlinx.coroutines.runBlocking
import timber.log.Timber

fun noOp() {
  /* No-op, obviously */
}

val Any.exhaustive: Any
  get() = this

fun ignoreExceptions(alsoLog: Boolean = true, block: () -> Unit) {
  try {
    block.invoke()
  } catch (e: Exception) {
    if (alsoLog) Timber.w(e)
  }
}

fun <T> runBlockingOrNull(block: suspend () -> T): T? = runBlocking {
  try {
    block.invoke()
  } catch (e: Exception) {
    Timber.w(e)
    null
  }
}
