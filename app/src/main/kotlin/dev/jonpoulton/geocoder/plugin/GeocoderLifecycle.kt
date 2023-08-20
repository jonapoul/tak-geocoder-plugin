package dev.jonpoulton.geocoder.plugin

import android.app.Activity
import android.content.Context
import com.atakmap.android.maps.MapActivity
import com.atakmap.android.maps.MapComponent
import com.atakmap.android.maps.MapView
import com.atakmap.app.preferences.ToolsPreferenceFragment
import dev.jonpoulton.alakazam.core.getCompatDrawable
import dev.jonpoulton.alakazam.tak.core.PluginContext
import dev.jonpoulton.alakazam.tak.plugin.CommonLifecycle
import dev.jonpoulton.alakazam.tak.plugin.CommonTree
import dev.jonpoulton.geocoder.di.DependencyGraph
import dev.jonpoulton.geocoder.di.GeocoderDependencyGraph
import dev.jonpoulton.geocoder.di.viewModels
import dev.jonpoulton.geocoder.settings.GeocoderSettingsFragment

class GeocoderLifecycle(context: Context) : CommonLifecycle() {
  private val viewModel by viewModels<GeocoderLifecycleViewModel>()
  override val pluginContext = PluginContext(context)
  override val mapComponents = emptyList<MapComponent>() // no UI!
  override val timberTree = CommonTree(prefix = "GEOCODER")

  private var widgetMapComponent: WidgetOverlayMapComponent? = null

  @Suppress("DEPRECATION")
  override fun onCreate(activity: Activity, mv: transapps.mapi.MapView) {
    super.onCreate(activity, mv)
    pluginContext.setTheme(R.style.Theme_Atak_Geocoder)

    /* Initialise DI */
    GeocoderDependencyGraph.init(pluginContext, appContext)

    /* Inject and set up plugin-wide dependencies */
    viewModel.setUp()

    /* Register settings screen */
    GeocoderSettingsFragment.initialise(injector = DependencyGraph)
    ToolsPreferenceFragment.register(
      ToolsPreferenceFragment.ToolPreference(
        pluginContext.getString(R.string.settings_title),
        pluginContext.getString(R.string.settings_summary),
        GeocoderSettingsFragment.KEY,
        pluginContext.getCompatDrawable(R.drawable.ic_plugin_icon),
        GeocoderSettingsFragment(pluginContext),
      ),
    )

    /* Register the widget */
    widgetMapComponent = WidgetOverlayMapComponent()
    mapView.mapActivity.registerMapComponent(widgetMapComponent)
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.tearDown()
    ToolsPreferenceFragment.unregister(GeocoderSettingsFragment.KEY)
    mapView.mapActivity.unregisterMapComponent(widgetMapComponent)
  }

  private val MapView.mapActivity: MapActivity
    get() = context as MapActivity
}
