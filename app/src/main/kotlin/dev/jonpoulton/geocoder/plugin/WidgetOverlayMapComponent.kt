@file:Suppress("DEPRECATION")

package dev.jonpoulton.geocoder.plugin

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.MotionEvent
import com.atakmap.android.maps.MapView
import com.atakmap.android.widgets.AbstractWidgetMapComponent
import com.atakmap.android.widgets.LinearLayoutWidget
import com.atakmap.android.widgets.MapWidget
import com.atakmap.android.widgets.RootLayoutWidget
import com.atakmap.android.widgets.TextWidget
import com.atakmap.app.SettingsActivity
import dev.jonpoulton.alakazam.core.collectFlow
import dev.jonpoulton.alakazam.core.exhaustive
import dev.jonpoulton.geocoder.di.DependencyGraph
import dev.jonpoulton.geocoder.di.viewModels
import dev.jonpoulton.geocoder.geocoding.GeocodedState
import dev.jonpoulton.geocoder.settings.GeocoderSettingsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import timber.log.Timber

class WidgetOverlayMapComponent : AbstractWidgetMapComponent(), MapWidget.OnClickListener {
  private val viewModel by viewModels<WidgetOverlayMapComponentViewModel>()
  private var layout: LinearLayoutWidget? = null
  private var widget: TextWidget? = null
  private var scope: CoroutineScope? = null
  private var job: Job? = null

  override fun onCreateWidgets(appContext: Context, intent: Intent?, mapView: MapView) {
    Timber.v("onCreateWidgets $intent")
    scope = CoroutineScope(Dispatchers.Main)
    val root = mapView.getComponentExtra("rootLayoutWidget") as RootLayoutWidget
    layout = root.getLayout(RootLayoutWidget.BOTTOM_RIGHT).getOrCreateLayout(LAYOUT_NAME) as LinearLayoutWidget

    widget = TextWidget().apply {
      name = "GeoCoder"
      textFormat = MapView.getTextFormat(Typeface.DEFAULT, FONT_SIZE)
      addOnClickListener(this@WidgetOverlayMapComponent)
    }
    layout?.addChildWidgetAt(0, widget)

    job?.cancel()
    job = scope?.collectFlow(viewModel.geocodedState) {
      widget?.handleState(it)
    }
  }

  private fun TextWidget.handleState(state: GeocodedState) {
    Timber.i("handleState $state")
    when (state) {
      is GeocodedState.NoPositionFound -> {
        isVisible = false
      }

      is GeocodedState.Visible -> {
        isVisible = true
        color = state.color
        text = state.string
      }
    }.exhaustive
  }

  override fun onDestroyWidgets(appContext: Context, mapView: MapView) {
    Timber.v("onDestroyWidgets")
    job?.cancel()
    job = null
    scope?.cancel()
    layout?.removeWidget(widget)
    widget = null
    layout = null
  }

  override fun onMapWidgetClick(widget: MapWidget, event: MotionEvent) {
    Timber.d("Launching settings screen: $event")
    SettingsActivity.start(
      GeocoderSettingsFragment.KEY,
      GeocoderSettingsFragment.KEY,
    )
  }

  private companion object {
    const val FONT_SIZE = 3
    const val LAYOUT_NAME = "Geocoder_H"
  }
}
