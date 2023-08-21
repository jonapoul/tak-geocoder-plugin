@file:Suppress("DEPRECATION")

package dev.jonpoulton.geocoder.geocoding

import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import com.atakmap.android.maps.MapView
import com.atakmap.android.widgets.AbstractWidgetMapComponent
import com.atakmap.android.widgets.LinearLayoutWidget
import com.atakmap.android.widgets.MapWidget
import com.atakmap.android.widgets.RootLayoutWidget
import com.atakmap.android.widgets.TextWidget
import dev.jonpoulton.alakazam.core.collectFlow
import dev.jonpoulton.alakazam.core.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

abstract class GeocoderWidgetMapComponent : AbstractWidgetMapComponent(), MapWidget.OnClickListener {
  private var layout: LinearLayoutWidget? = null
  private var widget: TextWidget? = null
  private var widgetScope: CoroutineScope? = null

  protected abstract val fontSize: Int
  protected abstract val layoutName: String
  protected abstract val layoutCorner: Int
  protected abstract val widgetName: String

  protected abstract fun geocodedState(): Flow<GeocodedState>
  protected abstract fun displayStateColor(state: GeocodedState): Boolean
  protected abstract fun TextWidget.setMargins()

  override fun onCreateWidgets(appContext: Context, intent: Intent?, mapView: MapView) {
    Timber.v("onCreateWidgets $intent")
    widgetScope = CoroutineScope(Dispatchers.Main)
    val root = mapView.getComponentExtra("rootLayoutWidget") as RootLayoutWidget
    layout = root.getLayout(layoutCorner).getOrCreateLayout(layoutName) as LinearLayoutWidget

    widget = TextWidget("", fontSize).apply {
      name = widgetName
      setMargins()
      addOnClickListener(this@GeocoderWidgetMapComponent)
    }
    layout?.addChildWidgetAt(0, widget)
    widgetScope?.collectFlow(geocodedState()) { state ->
      widget?.handleGeocodedState(state, includeColor = displayStateColor(state))
    }
  }

  override fun onDestroyWidgets(appContext: Context, mapView: MapView) {
    Timber.v("onDestroyWidgets")
    widgetScope?.cancel()
    layout?.removeWidget(widget)
    widget = null
    layout = null
  }

  override fun onMapWidgetClick(widget: MapWidget, event: MotionEvent) {
    Timber.d("onMapWidgetClick $event")
  }

  private fun TextWidget.handleGeocodedState(state: GeocodedState, includeColor: Boolean) {
    Timber.v("handleState $state")
    when (state) {
      is GeocodedState.Invisible -> {
        isVisible = false
      }

      is GeocodedState.Visible -> {
        isVisible = true
        if (includeColor) {
          color = state.color
        }
        text = state.string
      }
    }.exhaustive
  }

  protected companion object {
    const val HorizontalMargin = 12f
    const val VerticalMargin = 8f
  }
}
