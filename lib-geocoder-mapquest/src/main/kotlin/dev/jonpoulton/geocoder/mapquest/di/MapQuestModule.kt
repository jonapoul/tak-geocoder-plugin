package dev.jonpoulton.geocoder.mapquest.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dev.jonpoulton.geocoder.geocoding.CustomHttpGeocoder
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.mapquest.MAP_QUEST_API_URL
import dev.jonpoulton.geocoder.mapquest.MapQuestGeocoder
import dev.jonpoulton.geocoder.mapquest.MapQuestPreferences
import dev.jonpoulton.geocoder.mapquest.api.MapQuestApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MapQuestModule {
  @Provides
  @Singleton
  internal fun api(builder: Retrofit.Builder): MapQuestApi {
    return builder
      .baseUrl(MAP_QUEST_API_URL)
      .build()
      .create(MapQuestApi::class.java)
  }

  @Provides
  @Singleton
  @IntoSet
  internal fun geocoder(geocoder: MapQuestGeocoder): CustomHttpGeocoder = geocoder

  @Provides
  @IntoSet
  internal fun initializable(prefs: MapQuestPreferences): Initializable = prefs
}
