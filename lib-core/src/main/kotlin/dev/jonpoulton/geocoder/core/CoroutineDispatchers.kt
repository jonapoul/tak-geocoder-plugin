package dev.jonpoulton.geocoder.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class MainDispatcher(delegate: CoroutineDispatcher) : CoroutineContext by delegate

class IODispatcher(delegate: CoroutineDispatcher) : CoroutineContext by delegate

class DefaultDispatcher(delegate: CoroutineDispatcher) : CoroutineContext by delegate
