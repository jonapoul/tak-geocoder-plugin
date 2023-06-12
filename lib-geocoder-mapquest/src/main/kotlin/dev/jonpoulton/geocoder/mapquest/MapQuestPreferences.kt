package dev.jonpoulton.geocoder.mapquest

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.NullableSerializer
import com.fredporciuncula.flow.preferences.Preference
import dev.jonpoulton.geocoder.core.IBuildConfig
import dev.jonpoulton.geocoder.core.PrefPair
import dev.jonpoulton.geocoder.core.getNullableObject
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.mapquest.model.MapQuestApiKey

internal class MapQuestPreferences(
  flowPrefs: FlowSharedPreferences,
  buildConfig: IBuildConfig,
) : Initializable {
  private val default = buildConfig.mapQuestApiKey?.let(::MapQuestApiKey)
  private val apiKeyPrefPair = PrefPair<MapQuestApiKey?>(key = "mapQuestApiKey", default = null)

  val apiKey: Preference<MapQuestApiKey?> = flowPrefs.getNullableObject(apiKeyPrefPair, ApiKeySerializer)

  init {
    init()
  }

  override fun init() {
    if (apiKey.get()?.toString().isNullOrBlank()) {
      apiKey.set(default)
    }
  }

  private object ApiKeySerializer : NullableSerializer<MapQuestApiKey> {
    override fun deserialize(serialized: String?) = serialized?.let(::MapQuestApiKey)
    override fun serialize(value: MapQuestApiKey?) = value?.toString()
  }
}
