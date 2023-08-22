package dev.jonpoulton.geocoder.tak.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface DaggerInjector {
  fun vmFactory(): ViewModelProvider.Factory
}

inline fun <reified VM : ViewModel> DaggerInjector.viewModels(): Lazy<VM> =
  lazy { vmFactory().create(VM::class.java) }
