package dev.jonpoulton.geocoder.w3w.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dev.jonpoulton.geocoder.geocoding.CustomHttpGeocoder
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.w3w.W3W_API_URL
import dev.jonpoulton.geocoder.w3w.WhatThreeWordsGeocoder
import dev.jonpoulton.geocoder.w3w.WhatThreeWordsPreferences
import dev.jonpoulton.geocoder.w3w.api.WhatThreeWordsApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class WhatThreeWordsModule {
  @Provides
  @Singleton
  internal fun api(builder: Retrofit.Builder): WhatThreeWordsApi {
    return builder
      .baseUrl(W3W_API_URL)
      .build()
      .create(WhatThreeWordsApi::class.java)
  }

  @Provides
  @Singleton
  @IntoSet
  internal fun geocoder(geocoder: WhatThreeWordsGeocoder): CustomHttpGeocoder = geocoder

  @Provides
  @IntoSet
  internal fun initializable(prefs: WhatThreeWordsPreferences): Initializable = prefs
}
