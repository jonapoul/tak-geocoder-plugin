package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.geocoding.CustomHttpGeocoder
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.positionstack.PositionStackGeocoder
import dev.jonpoulton.geocoder.positionstack.PositionStackPreferences
import dev.jonpoulton.geocoder.positionstack.api.PositionStackApi
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Using HTTP, not HTTPS because the free plan doesn't allow HTTPS.
 * TODO: make HTTPS support a configurable option
 */
internal const val POSITION_STACK_API_URL = "http://api.positionstack.com/"

val positionStackModule = module {
  single<PositionStackApi> {
    get<Retrofit.Builder>()
      .baseUrl(POSITION_STACK_API_URL)
      .build()
      .create(PositionStackApi::class.java)
  }

  single { PositionStackGeocoder(get(), get(), get(), get()) } bind CustomHttpGeocoder::class

  factory { PositionStackPreferences(get(), get()) } bind Initializable::class
}
