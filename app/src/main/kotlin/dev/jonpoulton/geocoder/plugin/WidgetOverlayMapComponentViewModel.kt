package dev.jonpoulton.geocoder.plugin

import dev.jonpoulton.alakazam.tak.ui.TakViewModel
import dev.jonpoulton.geocoder.geocoding.GeocodedState
import dev.jonpoulton.geocoder.geocoding.LocationMonitor
import dev.jonpoulton.geocoder.settings.PluginPreferences
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class WidgetOverlayMapComponentViewModel @Inject constructor(
  locationMonitor: LocationMonitor,
  pluginPreferences: PluginPreferences,
) : TakViewModel() {
  val geocodedState = combine(
    pluginPreferences.includeTag.asFlow(),
    locationMonitor.geocodedState,
  ) { includeTag, state ->
    when {
      state is GeocodedState.Visible && includeTag -> GeocodedState.Tagged(state)
      else -> state
    }
  }
}
