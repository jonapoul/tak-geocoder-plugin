package dev.jonpoulton.geocoder.plugin

import com.atakmap.android.maps.MapActivity
import com.atakmap.android.maps.MapView
import com.atakmap.app.preferences.ToolsPreferenceFragment
import dev.jonpoulton.alakazam.core.getCompatDrawable
import dev.jonpoulton.geocoder.di.DependencyGraphInstance
import dev.jonpoulton.geocoder.di.GeocoderDependencyGraph
import dev.jonpoulton.geocoder.di.viewModels
import dev.jonpoulton.geocoder.settings.GeocoderSettingsFragment
import dev.jonpoulton.geocoder.tak.AppContext
import dev.jonpoulton.geocoder.tak.PluginContext
import dev.jonpoulton.geocoder.widget.centre.MapCentreWidgetMapComponent
import dev.jonpoulton.geocoder.widget.self.SelfMarkerWidgetMapComponent
import gov.tak.api.plugin.IPlugin
import gov.tak.api.plugin.IServiceController
import timber.log.Timber

class GeocoderPlugin(serviceController: IServiceController) : IPlugin {
  private val viewModel by viewModels<GeocoderLifecycleViewModel>()

  private val pluginContext = PluginContext(serviceController)
  private val timberTree = GeocoderTree()

  private lateinit var mapView: MapView
  private lateinit var appContext: AppContext

  private var selfMarkerWidgetMapComponent: SelfMarkerWidgetMapComponent? = null
  private var mapCentreWidgetMapComponent: MapCentreWidgetMapComponent? = null

  override fun onStart() {
    Timber.v("onStart")
    mapView = MapView.getMapView()
    appContext = AppContext(mapView)
    Timber.plant(timberTree)
    pluginContext.setTheme(R.style.Theme_Atak_Geocoder)
    GeocoderDependencyGraph.init(pluginContext, appContext)
    viewModel.setUp()

    /* Register settings screen */
    GeocoderSettingsFragment.initialise(injector = DependencyGraphInstance)
    ToolsPreferenceFragment.register(
      ToolsPreferenceFragment.ToolPreference(
        pluginContext.getString(R.string.settings_title),
        pluginContext.getString(R.string.settings_summary),
        GeocoderSettingsFragment.KEY,
        pluginContext.getCompatDrawable(R.drawable.ic_plugin_icon),
        GeocoderSettingsFragment(pluginContext),
      ),
    )

    /* Register the widgets */
    selfMarkerWidgetMapComponent = SelfMarkerWidgetMapComponent(injector = DependencyGraphInstance)
    mapCentreWidgetMapComponent = MapCentreWidgetMapComponent(injector = DependencyGraphInstance)
    mapView.mapActivity.registerMapComponent(selfMarkerWidgetMapComponent)
    mapView.mapActivity.registerMapComponent(mapCentreWidgetMapComponent)
  }

  override fun onStop() {
    Timber.v("onStop")
    Timber.uproot(timberTree)
    viewModel.tearDown()
    ToolsPreferenceFragment.unregister(GeocoderSettingsFragment.KEY)
    mapView.mapActivity.unregisterMapComponent(selfMarkerWidgetMapComponent)
    mapView.mapActivity.unregisterMapComponent(mapCentreWidgetMapComponent)
  }

  private val MapView.mapActivity: MapActivity
    get() = context as MapActivity
}
