package dev.jonpoulton.geocoder.di

import com.atakmap.android.ipc.AtakBroadcast
import com.atakmap.android.maps.MapView
import com.atakmap.android.user.geocode.GeocodeManager
import dev.jonpoulton.geocoder.core.AppContext
import org.koin.dsl.module

val atakModule = module {
  single { AtakBroadcast.getInstance() }
  single { MapView.getMapView() }
  single { GeocodeManager.getInstance(get<AppContext>()) }
}
