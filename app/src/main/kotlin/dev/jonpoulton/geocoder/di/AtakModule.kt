package dev.jonpoulton.geocoder.di

import com.atakmap.android.ipc.AtakBroadcast
import com.atakmap.android.maps.MapView
import com.atakmap.android.user.geocode.GeocodeManager
import dagger.Module
import dagger.Provides
import dev.jonpoulton.geocoder.tak.AppContext

@Module
internal class AtakModule {
  @Provides
  fun atakBroadcast(): AtakBroadcast = AtakBroadcast.getInstance()

  @Provides
  fun mapView(): MapView = MapView.getMapView()

  @Provides
  fun geocodeManager(appContext: AppContext): GeocodeManager = GeocodeManager.getInstance(appContext)
}
