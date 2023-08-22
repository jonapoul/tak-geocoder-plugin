package dev.jonpoulton.geocoder.plugin

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
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
import timber.log.Timber

@Suppress("DEPRECATION")
class GeocoderLifecycle(context: Context) : transapps.maps.plugin.lifecycle.Lifecycle {
  private val viewModel by viewModels<GeocoderLifecycleViewModel>()

  private val pluginContext = PluginContext(context)
  private val timberTree = GeocoderTree()

  private lateinit var mapView: MapView
  private lateinit var appContext: AppContext

  private var selfMarkerWidgetMapComponent: SelfMarkerWidgetMapComponent? = null
  private var mapCentreWidgetMapComponent: MapCentreWidgetMapComponent? = null

  @Suppress("DEPRECATION")
  override fun onCreate(activity: Activity, mv: transapps.mapi.MapView) {
    Timber.v("onCreate")
    mapView = mv.view as? MapView ?: error("Plugin is only compatible with ATAK MapView")
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

  override fun onDestroy() {
    Timber.uproot(timberTree)
    viewModel.tearDown()
    ToolsPreferenceFragment.unregister(GeocoderSettingsFragment.KEY)
    mapView.mapActivity.unregisterMapComponent(selfMarkerWidgetMapComponent)
    mapView.mapActivity.unregisterMapComponent(mapCentreWidgetMapComponent)
  }

  override fun onStart() {
    Timber.v("onStart")
  }

  override fun onPause() {
    Timber.v("onPause")
  }

  override fun onResume() {
    Timber.v("onResume")
  }

  override fun onStop() {
    Timber.v("onStop")
  }

  override fun onConfigurationChanged(configuration: Configuration) {
    Timber.v("onConfigurationChanged $configuration")
  }

  override fun onFinish() {
    Timber.v("onFinish")
  }

  private val MapView.mapActivity: MapActivity
    get() = context as MapActivity
}
