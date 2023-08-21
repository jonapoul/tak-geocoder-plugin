package dev.jonpoulton.geocoder.widget.self

import com.atakmap.android.maps.MapView
import com.atakmap.android.user.geocode.GeocodeManager
import com.atakmap.coremap.maps.coords.GeoPoint
import dev.jonpoulton.alakazam.core.IODispatcher
import dev.jonpoulton.geocoder.geocoding.LocationMonitor
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelfMarkerLocationMonitor @Inject constructor(
  mapView: MapView,
  geocodeManager: GeocodeManager,
  scope: CoroutineScope,
  io: IODispatcher,
) : LocationMonitor(mapView, geocodeManager, scope, io) {

  override fun getPoint(): GeoPoint? = mapView.selfMarker.point
  override fun refreshPeriodMs(): Long = REFRESH_PERIOD_MS

  private companion object {
    const val REFRESH_PERIOD_MS = 3_000L
  }
}
