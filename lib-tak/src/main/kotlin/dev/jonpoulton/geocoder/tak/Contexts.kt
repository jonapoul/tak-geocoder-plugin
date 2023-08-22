package dev.jonpoulton.geocoder.tak

import android.content.Context
import android.content.ContextWrapper
import com.atak.plugins.impl.PluginContextProvider
import com.atakmap.android.maps.MapView
import gov.tak.api.plugin.IServiceController

class AppContext : ContextWrapper {
  constructor(mapView: MapView) : super(mapView.context)
  constructor(context: Context) : super(context)

  fun isTablet(): Boolean = resources.getBoolean(com.atakmap.app.R.bool.isTablet)
}

class PluginContext : ContextWrapper {
  constructor(context: Context) : super(context)
  constructor(svc: IServiceController) : super(svc.getService(PluginContextProvider::class.java).pluginContext)
}
