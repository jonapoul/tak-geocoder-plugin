package dev.jonpoulton.geocoder.widget.centre

import dev.jonpoulton.geocoder.geocoding.GeocodedState
import dev.jonpoulton.geocoder.tak.ui.TakViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MapCentreWidgetMapComponentViewModel @Inject constructor(
  private val mapCentreLocationMonitor: MapCentreLocationMonitor,
  private val mapCentrePreferences: MapCentrePreferences,
) : TakViewModel() {
  val geocodedState: Flow<GeocodedState>
    get() = combine(
      mapCentrePreferences.designateMapCentre.asFlow(),
      mapCentreLocationMonitor.geocodedState,
    ) { showWidget, state ->
      if (!showWidget) {
        GeocodedState.HideWidget
      } else {
        state
      }
    }
}
