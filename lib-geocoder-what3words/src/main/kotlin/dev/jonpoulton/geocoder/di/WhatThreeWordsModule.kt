package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.geocoding.CustomHttpGeocoder
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.w3w.WhatThreeWordsGeocoder
import dev.jonpoulton.geocoder.w3w.WhatThreeWordsPreferences
import dev.jonpoulton.geocoder.w3w.api.WhatThreeWordsApi
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val W3W_API_URL = "https://api.what3words.com/"

val whatThreeWordsModule = module {
  single<WhatThreeWordsApi> {
    get<Retrofit.Builder>()
      .baseUrl(W3W_API_URL)
      .build()
      .create(WhatThreeWordsApi::class.java)
  }

  singleOf(::WhatThreeWordsGeocoder) bind CustomHttpGeocoder::class

  factoryOf(::WhatThreeWordsPreferences) bind Initializable::class
}
