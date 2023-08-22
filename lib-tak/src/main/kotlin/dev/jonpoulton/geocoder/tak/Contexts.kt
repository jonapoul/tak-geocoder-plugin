package dev.jonpoulton.geocoder.tak

import android.content.Context
import android.content.ContextWrapper
import com.atakmap.android.maps.MapView

class AppContext : ContextWrapper {
  constructor(mapView: MapView) : super(mapView.context)
  constructor(context: Context) : super(context)

  fun isTablet(): Boolean = resources.getBoolean(com.atakmap.app.R.bool.isTablet)
}

class PluginContext(context: Context) : ContextWrapper(context)
