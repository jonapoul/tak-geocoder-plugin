package dev.jonpoulton.geocoder.di

import androidx.lifecycle.ViewModel
import dev.jonpoulton.alakazam.tak.di.DaggerViewModelFactory
import dev.jonpoulton.geocoder.centre.MapCentreWidgetMapComponentViewModel
import dev.jonpoulton.geocoder.plugin.AddressWidgetMapComponentViewModel
import dev.jonpoulton.geocoder.plugin.GeocoderLifecycleViewModel
import dev.jonpoulton.geocoder.settings.GeocoderSettingsViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class GeocoderViewModelFactory @Inject constructor(
  lifecycle: Provider<GeocoderLifecycleViewModel>,
  addressWidget: Provider<AddressWidgetMapComponentViewModel>,
  mapCentreWidget: Provider<MapCentreWidgetMapComponentViewModel>,
  settings: Provider<GeocoderSettingsViewModel>,
) : DaggerViewModelFactory() {
  override val providers = mapOf<Class<out ViewModel>, Provider<out ViewModel>>(
    GeocoderLifecycleViewModel::class.java to lifecycle,
    AddressWidgetMapComponentViewModel::class.java to addressWidget,
    MapCentreWidgetMapComponentViewModel::class.java to mapCentreWidget,
    GeocoderSettingsViewModel::class.java to settings,
  )
}
