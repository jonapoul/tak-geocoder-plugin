package dev.jonpoulton.geocoder.widget.self

import dev.jonpoulton.geocoder.geocoding.GeocodedState
import dev.jonpoulton.geocoder.settings.PluginPreferences
import dev.jonpoulton.geocoder.tak.ui.TakViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class SelfMarkerWidgetMapComponentViewModel @Inject constructor(
  private val locationMonitor: SelfMarkerLocationMonitor,
  private val pluginPreferences: PluginPreferences,
) : TakViewModel() {
  val geocodedState: Flow<GeocodedState>
    get() = combine(
      pluginPreferences.includeTag.asFlow(),
      locationMonitor.geocodedState,
    ) { includeTag, state ->
      when {
        state is GeocodedState.Visible && includeTag -> GeocodedState.Tagged(state)
        else -> state
      }
    }.distinctUntilChanged()
}
