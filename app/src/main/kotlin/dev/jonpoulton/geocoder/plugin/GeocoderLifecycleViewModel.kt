package dev.jonpoulton.geocoder.plugin

import dev.jonpoulton.alakazam.tak.ui.TakViewModel
import dev.jonpoulton.geocoder.GeocoderRegistrar
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.geocoding.LocationMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class GeocoderLifecycleViewModel @Inject constructor(
  private val pluginScope: CoroutineScope,
  private val locationMonitor: LocationMonitor,
  private val geocoderRegistrar: GeocoderRegistrar,
  private val initializables: Set<@JvmSuppressWildcards Initializable>,
) : TakViewModel() {
  fun setUp() {
    /* Begin gathering data */
    locationMonitor.startGeocoding()

    /* Initialise API key, if not already filled-in */
    initializables.forEach { it.init() }

    /* Dynamically register/deregister geocoders based on preference values */
    geocoderRegistrar.start()
  }

  fun tearDown() {
    /* Stop gathering data */
    locationMonitor.stopGeocoding()

    /* Stop monitoring geocoder settings */
    geocoderRegistrar.stop()

    /* Kill any ongoing jobs */
    pluginScope.cancel()
  }
}
