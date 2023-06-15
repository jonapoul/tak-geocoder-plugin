package dev.jonpoulton.geocoder.di

import dev.jonpoulton.geocoder.geocoding.CustomHttpGeocoder
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.mapquest.MapQuestGeocoder
import dev.jonpoulton.geocoder.mapquest.MapQuestPreferences
import dev.jonpoulton.geocoder.mapquest.api.MapQuestApi
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val MAP_QUEST_API_URL = "https://www.mapquestapi.com/"

val mapQuestModule = module {
  single<MapQuestApi> {
    get<Retrofit.Builder>()
      .baseUrl(MAP_QUEST_API_URL)
      .build()
      .create(MapQuestApi::class.java)
  }

  singleOf(::MapQuestGeocoder) bind CustomHttpGeocoder::class

  factoryOf(::MapQuestPreferences) bind Initializable::class
}
