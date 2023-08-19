package dev.jonpoulton.geocoder.di

import androidx.lifecycle.ViewModel
import dev.jonpoulton.alakazam.tak.di.DaggerViewModelFactory
import dev.jonpoulton.geocoder.plugin.GeocoderLifecycleViewModel
import dev.jonpoulton.geocoder.plugin.WidgetOverlayMapComponentViewModel
import dev.jonpoulton.geocoder.settings.GeocoderSettingsViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class GeocoderViewModelFactory @Inject constructor(
  mapComponent: Provider<GeocoderLifecycleViewModel>,
  widget: Provider<WidgetOverlayMapComponentViewModel>,
  settings: Provider<GeocoderSettingsViewModel>,
) : DaggerViewModelFactory() {
  override val providers = mapOf<Class<out ViewModel>, Provider<out ViewModel>>(
    GeocoderLifecycleViewModel::class.java to mapComponent,
    WidgetOverlayMapComponentViewModel::class.java to widget,
    GeocoderSettingsViewModel::class.java to settings,
  )
}

inline fun <reified VM : ViewModel> viewModels(): Lazy<VM> = lazy { DependencyGraph.vmFactory().create(VM::class.java) }
