package dev.jonpoulton.geocoder.plugin

import dev.jonpoulton.alakazam.core.collectFlow
import dev.jonpoulton.geocoder.GeocoderRegistrar
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.tak.ui.TakViewModel
import dev.jonpoulton.geocoder.widget.centre.MapCentreLocationMonitor
import dev.jonpoulton.geocoder.widget.centre.MapCentrePreferences
import dev.jonpoulton.geocoder.widget.self.SelfMarkerLocationMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class GeocoderLifecycleViewModel @Inject constructor(
  private val pluginScope: CoroutineScope,
  private val selfLocationMonitor: SelfMarkerLocationMonitor,
  private val mapCentreLocationMonitor: MapCentreLocationMonitor,
  private val mapCentrePreferences: MapCentrePreferences,
  private val geocoderRegistrar: GeocoderRegistrar,
  private val initializables: Set<@JvmSuppressWildcards Initializable>,
) : TakViewModel() {
  fun setUp() {
    /* Begin geocoding this device's location */
    selfLocationMonitor.startGeocoding()

    /* Also geocode the map's centre-point, only if the centre-point overlay is enabled in ATAK */
    pluginScope.collectFlow(mapCentrePreferences.designateMapCentre.asFlow()) { showMapCentreWidget ->
      if (showMapCentreWidget) {
        mapCentreLocationMonitor.startGeocoding()
      } else {
        mapCentreLocationMonitor.stopGeocoding()
      }
    }

    /* Initialise API key, if not already filled-in */
    initializables.forEach { it.init() }

    /* Dynamically register/deregister geocoders based on preference values */
    geocoderRegistrar.start()
  }

  fun tearDown() {
    /* Stop gathering data */
    selfLocationMonitor.stopGeocoding()

    /* Stop monitoring geocoder settings */
    geocoderRegistrar.stop()

    /* Kill any ongoing jobs */
    pluginScope.cancel()
  }
}
