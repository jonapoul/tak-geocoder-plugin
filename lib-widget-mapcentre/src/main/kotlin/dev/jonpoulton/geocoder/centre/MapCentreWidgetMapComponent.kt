@file:Suppress("DEPRECATION")

package dev.jonpoulton.geocoder.centre

import android.view.MotionEvent
import com.atakmap.android.widgets.MapWidget
import com.atakmap.android.widgets.RootLayoutWidget
import com.atakmap.android.widgets.TextWidget
import dev.jonpoulton.alakazam.tak.di.DaggerInjector
import dev.jonpoulton.alakazam.tak.di.viewModels
import dev.jonpoulton.geocoder.geocoding.GeocodedState
import dev.jonpoulton.geocoder.geocoding.GeocoderWidgetMapComponent
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class MapCentreWidgetMapComponent(
  injector: DaggerInjector,
) : GeocoderWidgetMapComponent() {
  private val viewModel by injector.viewModels<MapCentreWidgetMapComponentViewModel>()

  override val fontSize = 2
  override val layoutName = "GeocoderMapCentre_H"
  override val layoutCorner = RootLayoutWidget.BOTTOM_LEFT
  override val widgetName = "GeocoderMapCentre"

  override fun geocodedState(): Flow<GeocodedState> = viewModel.geocodedState

  override fun displayStateColor(state: GeocodedState): Boolean = state !is GeocodedState.HasGeocoder

  override fun TextWidget.setMargins() {
    setMargins(HorizontalMargin, VerticalMargin, 0f, VerticalMargin)
  }

  override fun onMapWidgetClick(widget: MapWidget, event: MotionEvent) {
    Timber.d("onMapWidgetClick $event")
  }
}
