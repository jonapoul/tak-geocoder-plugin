package dev.jonpoulton.geocoder.positionstack

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.NullableSerializer
import com.fredporciuncula.flow.preferences.Preference
import dev.jonpoulton.geocoder.core.IBuildConfig
import dev.jonpoulton.geocoder.core.PrefPair
import dev.jonpoulton.geocoder.core.getNullableObject
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.positionstack.model.PositionStackApiKey

internal class PositionStackPreferences(
  flowPrefs: FlowSharedPreferences,
  buildConfig: IBuildConfig,
) : Initializable {
  private val default = buildConfig.positionStackApiKey?.let(::PositionStackApiKey)
  private val apiKeyPrefPair = PrefPair<PositionStackApiKey?>(key = "positionStackApiKey", default = null)

  val apiKey: Preference<PositionStackApiKey?> = flowPrefs.getNullableObject(apiKeyPrefPair, ApiKeySerializer)

  init {
    init()
  }

  override fun init() {
    if (apiKey.get()?.toString().isNullOrBlank()) {
      apiKey.set(default)
    }
  }

  private object ApiKeySerializer : NullableSerializer<PositionStackApiKey> {
    override fun deserialize(serialized: String?) = serialized?.let(::PositionStackApiKey)
    override fun serialize(value: PositionStackApiKey?) = value?.toString()
  }
}
