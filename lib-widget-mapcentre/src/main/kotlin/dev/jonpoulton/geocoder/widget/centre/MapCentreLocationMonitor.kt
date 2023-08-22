package dev.jonpoulton.geocoder.widget.centre

import com.atakmap.android.maps.MapView
import com.atakmap.android.user.geocode.GeocodeManager
import com.atakmap.coremap.maps.coords.GeoPoint
import dev.jonpoulton.alakazam.core.IODispatcher
import dev.jonpoulton.geocoder.geocoding.LocationMonitor
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapCentreLocationMonitor @Inject constructor(
  mapView: MapView,
  geocodeManager: GeocodeManager,
  scope: CoroutineScope,
  io: IODispatcher,
  private val mapCentrePreferences: MapCentrePreferences,
) : LocationMonitor(mapView, geocodeManager, scope, io) {

  override val tag = "MapCentre"

  override fun geocodingEnabled(): Boolean = mapCentrePreferences.designateMapCentre.get()

  override fun getPoint(): GeoPoint? = mapView.centerPoint.get()

  override fun refreshPeriodMs(): Long = REFRESH_PERIOD_MS

  private companion object {
    const val REFRESH_PERIOD_MS = 1_000L
  }
}
