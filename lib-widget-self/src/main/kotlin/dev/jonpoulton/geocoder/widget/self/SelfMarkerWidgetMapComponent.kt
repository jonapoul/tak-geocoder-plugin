@file:Suppress("DEPRECATION")

package dev.jonpoulton.geocoder.widget.self

import android.view.MotionEvent
import com.atakmap.android.widgets.MapWidget
import com.atakmap.android.widgets.RootLayoutWidget
import com.atakmap.android.widgets.TextWidget
import com.atakmap.app.SettingsActivity
import dev.jonpoulton.geocoder.geocoding.GeocodedState
import dev.jonpoulton.geocoder.geocoding.GeocoderWidgetMapComponent
import dev.jonpoulton.geocoder.settings.GeocoderSettingsFragment
import dev.jonpoulton.geocoder.tak.di.DaggerInjector
import dev.jonpoulton.geocoder.tak.di.viewModels
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class SelfMarkerWidgetMapComponent(
  injector: DaggerInjector,
) : GeocoderWidgetMapComponent() {
  private val viewModel by injector.viewModels<SelfMarkerWidgetMapComponentViewModel>()

  override val fontSize = 2
  override val layoutName = "Geocoder_H"
  override val layoutCorner = RootLayoutWidget.BOTTOM_RIGHT
  override val widgetName = "Geocoder"

  override fun geocodedState(): Flow<GeocodedState> = viewModel.geocodedState

  override fun displayStateColor(state: GeocodedState): Boolean = true

  override fun TextWidget.setMargins() {
    setMargins(0f, VerticalMargin, HorizontalMargin, VerticalMargin)
  }

  override fun onMapWidgetClick(widget: MapWidget, event: MotionEvent) {
    Timber.d("Launching settings screen: $event")
    SettingsActivity.start(
      GeocoderSettingsFragment.KEY,
      GeocoderSettingsFragment.KEY,
    )
  }
}
