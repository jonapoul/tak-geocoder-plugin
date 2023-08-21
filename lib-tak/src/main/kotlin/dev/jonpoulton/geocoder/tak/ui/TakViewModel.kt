package dev.jonpoulton.geocoder.tak.ui

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import timber.log.Timber

abstract class TakViewModel : ViewModel() {
  protected var viewModelScope: CoroutineScope = buildScope()
    private set

  init {
    Timber.v("init ${javaClass.canonicalName}")
  }

  @CallSuper
  open fun init() {
    if (!viewModelScope.isActive) {
      Timber.v("viewModelScope isn't active, recreating...")
      viewModelScope = buildScope()
    }
  }

  override fun onCleared() {
    Timber.v("onCleared ${javaClass.canonicalName}")
    super.onCleared()
    viewModelScope.cancel()
  }

  private fun buildScope(): CoroutineScope = CoroutineScope(context = Dispatchers.Main.immediate)
}
