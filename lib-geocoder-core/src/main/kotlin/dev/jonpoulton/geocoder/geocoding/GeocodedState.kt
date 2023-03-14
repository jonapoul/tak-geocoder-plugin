package dev.jonpoulton.geocoder.geocoding

import com.atakmap.android.user.geocode.GeocodeManager.Geocoder
import gov.tak.platform.graphics.Color

sealed interface GeocodedState {

  sealed interface Visible : GeocodedState {
    val color: Int
    val string: String
  }

  sealed interface HasGeocoder : GeocodedState {
    val geocoder: Geocoder
  }

  object NoPositionFound : GeocodedState

  data class Working(
    override val geocoder: Geocoder,
  ) : Visible, HasGeocoder {
    override val color: Int = Color.YELLOW
    override val string: String = "Geocoding..."
  }

  data class Success(
    override val string: String,
    override val geocoder: Geocoder,
  ) : Visible, HasGeocoder {
    override val color: Int = Color.CYAN
  }

  data class Failed(
    override val string: String,
    override val geocoder: Geocoder,
  ) : Visible, HasGeocoder {
    override val color: Int = Color.RED
  }

  object NoGeocoders : Visible {
    override val color: Int = Color.parseColor("#FF8000") // Orange
    override val string: String = "No registered geocoders"
  }

  data class NotAvailable(
    override val geocoder: Geocoder,
  ) : Visible, HasGeocoder {
    override val color: Int = Color.parseColor("#FF8000") // Orange
    override val string: String = "Geocoding not supported"
  }
}
