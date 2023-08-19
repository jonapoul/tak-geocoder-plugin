package dev.jonpoulton.geocoder.mapquest

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.Preference
import dev.jonpoulton.alakazam.prefs.PrefPair
import dev.jonpoulton.alakazam.prefs.SimpleNullableStringSerializer
import dev.jonpoulton.alakazam.prefs.getNullableObject
import dev.jonpoulton.geocoder.core.GeocoderBuildConfig
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.mapquest.model.MapQuestApiKey
import javax.inject.Inject

internal class MapQuestPreferences @Inject constructor(
  flowPrefs: FlowSharedPreferences,
  buildConfig: GeocoderBuildConfig,
) : Initializable {
  private val default = buildConfig.mapQuestApiKey?.let(::MapQuestApiKey)
  private val apiKeyPrefPair = PrefPair<MapQuestApiKey?>(key = "mapQuestApiKey", default = null)

  val apiKey: Preference<MapQuestApiKey?> = flowPrefs.getNullableObject(apiKeyPrefPair, ApiKeySerializer)

  override fun init() {
    if (apiKey.get()?.toString().isNullOrBlank()) {
      apiKey.set(default)
    }
  }

  private object ApiKeySerializer : SimpleNullableStringSerializer<MapQuestApiKey>(::MapQuestApiKey)
}
