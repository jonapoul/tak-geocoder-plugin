package dev.jonpoulton.geocoder.plugin

import android.content.Context
import android.content.Intent
import com.atakmap.android.dropdown.DropDownMapComponent
import com.atakmap.android.maps.MapActivity
import com.atakmap.android.maps.MapView
import dev.jonpoulton.geocoder.GeocoderRegistrar
import dev.jonpoulton.geocoder.core.PluginContext
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.geocoding.LocationMonitor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class GeocoderMapComponent(
  private val pluginContext: PluginContext,
) : DropDownMapComponent(), KoinComponent {

  private val locationMonitor by inject<LocationMonitor>()
  private val geocoderRegistrar by inject<GeocoderRegistrar>()
  private val initializables by lazy { getKoin().getAll<Initializable>() }

  private var widgetMapComponent: WidgetOverlayMapComponent? = null

  override fun onCreate(context: Context, intent: Intent?, mapView: MapView) {
    pluginContext.setTheme(R.style.Theme_Atak_Geocoder)
    super.onCreate(pluginContext, intent, mapView)
    Timber.d("onCreate $intent")

    /* Begin gathering data */
    locationMonitor.startGeocoding()

    /* Initialise API key, if not already filled-in */
    initializables.forEach { it.init() }

    /* Register the widget */
    widgetMapComponent = WidgetOverlayMapComponent()
    mapView.mapActivity.registerMapComponent(widgetMapComponent)

    /* Dynamically register/deregister geocoders based on preference values */
    geocoderRegistrar.start()
  }

  override fun onDestroyImpl(context: Context?, mapView: MapView) {
    Timber.d("onDestroyImpl")
    super.onDestroyImpl(context, mapView)

    /* Stop gathering data */
    locationMonitor.stopGeocoding()

    /* Unregister widget overlay */
    mapView.mapActivity.unregisterMapComponent(widgetMapComponent)

    /* Stop monitoring geocoder settings */
    geocoderRegistrar.stop()
  }

  private val MapView.mapActivity: MapActivity
    get() = context as MapActivity
}
