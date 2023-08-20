package dev.jonpoulton.geocoder.plugin

import dev.jonpoulton.alakazam.tak.ui.TakViewModel
import dev.jonpoulton.geocoder.geocoding.GeocodedState
import dev.jonpoulton.geocoder.geocoding.SelfLocationMonitor
import dev.jonpoulton.geocoder.settings.PluginPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class AddressWidgetMapComponentViewModel @Inject constructor(
  private val selfLocationMonitor: SelfLocationMonitor,
  private val pluginPreferences: PluginPreferences,
) : TakViewModel() {
  val geocodedState: Flow<GeocodedState>
    get() = combine(
      pluginPreferences.includeTag.asFlow(),
      selfLocationMonitor.geocodedState,
    ) { includeTag, state ->
      when {
        state is GeocodedState.Visible && includeTag -> GeocodedState.Tagged(state)
        else -> state
      }
    }.distinctUntilChanged()
}
