@file:Suppress("DEPRECATION")

package dev.jonpoulton.geocoder.plugin

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import com.atakmap.android.maps.MapComponent
import com.atakmap.android.maps.MapView
import com.atakmap.app.preferences.ToolsPreferenceFragment
import dev.jonpoulton.geocoder.core.AppContext
import dev.jonpoulton.geocoder.core.PluginContext
import dev.jonpoulton.geocoder.core.getCompatDrawable
import dev.jonpoulton.geocoder.di.atakModule
import dev.jonpoulton.geocoder.di.buildConfigModule
import dev.jonpoulton.geocoder.di.contextModule
import dev.jonpoulton.geocoder.di.coroutineModule
import dev.jonpoulton.geocoder.di.geocodingModule
import dev.jonpoulton.geocoder.di.httpModule
import dev.jonpoulton.geocoder.di.mapQuestModule
import dev.jonpoulton.geocoder.di.otherModule
import dev.jonpoulton.geocoder.di.pluginModule
import dev.jonpoulton.geocoder.di.positionStackModule
import dev.jonpoulton.geocoder.di.preferencesModule
import dev.jonpoulton.geocoder.di.settingsModule
import dev.jonpoulton.geocoder.di.systemServiceModule
import dev.jonpoulton.geocoder.di.whatThreeWordsModule
import dev.jonpoulton.geocoder.settings.GeocoderSettingsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import timber.log.Timber
import transapps.maps.plugin.lifecycle.Lifecycle

class GeocoderLifecycle(context: Context) : Lifecycle, KoinComponent {
  private val pluginContext = PluginContext(context)
  private val mapComponents = arrayListOf<MapComponent>()

  private lateinit var mapView: MapView

  override fun onConfigurationChanged(configuration: Configuration) {
    mapComponents.forEach { it.onConfigurationChanged(configuration) }
  }

  override fun onCreate(activity: Activity, mv: transapps.mapi.MapView?) {
    mapView = mv?.view as MapView
    Timber.plant(GeocoderDebugTree())
    val appContext = AppContext(mapView)
    startKoin {
      modules(
        atakModule,
        buildConfigModule,
        contextModule(appContext, pluginContext),
        coroutineModule,
        geocodingModule,
        httpModule,
        mapQuestModule,
        otherModule,
        pluginModule,
        positionStackModule,
        preferencesModule,
        settingsModule,
        systemServiceModule(appContext),
        whatThreeWordsModule,
      )
    }

    mapComponents.addAll(
      listOf(
        ::GeocoderMapComponent,
      ).map { constructor ->
        constructor.invoke(pluginContext)
      },
    )

    mapComponents.forEach {
      try {
        it.onCreate(pluginContext, activity.intent, mapView)
      } catch (e: Exception) {
        Timber.e(e, "Failed creating $it")
      }
    }

    ToolsPreferenceFragment.register(
      ToolsPreferenceFragment.ToolPreference(
        pluginContext.getString(R.string.settings_title),
        pluginContext.getString(R.string.settings_summary),
        GeocoderSettingsFragment.KEY,
        pluginContext.getCompatDrawable(R.drawable.ic_plugin_icon),
        GeocoderSettingsFragment(pluginContext),
      ),
    )
  }

  override fun onDestroy() {
    mapComponents.forEach { it.onDestroy(pluginContext, mapView) }
    mapComponents.clear()

    ToolsPreferenceFragment.unregister(GeocoderSettingsFragment.KEY)
    Timber.uprootAll()
    get<CoroutineScope>().cancel()
    stopKoin()
  }

  override fun onFinish() {
//     mapComponents.forEach { it.onFinish(pluginContext, mapView) }
  }

  override fun onPause() {
    mapComponents.forEach { it.onPause(pluginContext, mapView) }
  }

  override fun onResume() {
    mapComponents.forEach { it.onResume(pluginContext, mapView) }
  }

  override fun onStart() {
    mapComponents.forEach { it.onStart(pluginContext, mapView) }
  }

  override fun onStop() {
    mapComponents.forEach { it.onStop(pluginContext, mapView) }
  }
}
