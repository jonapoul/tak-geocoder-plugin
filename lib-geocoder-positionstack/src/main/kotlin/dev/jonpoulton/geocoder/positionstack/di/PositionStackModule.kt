package dev.jonpoulton.geocoder.positionstack.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dev.jonpoulton.geocoder.geocoding.CustomHttpGeocoder
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.positionstack.POSITION_STACK_API_URL
import dev.jonpoulton.geocoder.positionstack.PositionStackGeocoder
import dev.jonpoulton.geocoder.positionstack.PositionStackPreferences
import dev.jonpoulton.geocoder.positionstack.api.PositionStackApi
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Using HTTP, not HTTPS because the free plan doesn't allow HTTPS.
 * TODO: make HTTPS support a configurable option
 */
@Module
class PositionStackModule {
  @Provides
  @Singleton
  internal fun api(builder: Retrofit.Builder): PositionStackApi {
    return builder
      .baseUrl(POSITION_STACK_API_URL)
      .build()
      .create(PositionStackApi::class.java)
  }

  @Provides
  @Singleton
  @IntoSet
  internal fun geocoder(geocoder: PositionStackGeocoder): CustomHttpGeocoder = geocoder

  @Provides
  @IntoSet
  internal fun initializable(prefs: PositionStackPreferences): Initializable = prefs
}
