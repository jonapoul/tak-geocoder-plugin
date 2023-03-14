package dev.jonpoulton.geocoder.core

import android.content.Context
import android.content.ContextWrapper
import com.atakmap.android.maps.MapView

class AppContext : ContextWrapper {
  constructor(mapView: MapView) : super(mapView.context)
  constructor(context: Context) : super(context)
}

class PluginContext(context: Context) : ContextWrapper(context)
