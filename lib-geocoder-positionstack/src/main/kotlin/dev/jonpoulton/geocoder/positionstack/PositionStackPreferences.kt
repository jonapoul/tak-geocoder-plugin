package dev.jonpoulton.geocoder.positionstack

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.Preference
import dev.jonpoulton.alakazam.prefs.PrefPair
import dev.jonpoulton.alakazam.prefs.SimpleNullableStringSerializer
import dev.jonpoulton.alakazam.prefs.getNullableObject
import dev.jonpoulton.geocoder.core.GeocoderBuildConfig
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.positionstack.model.PositionStackApiKey
import javax.inject.Inject

internal class PositionStackPreferences @Inject constructor(
  flowPrefs: FlowSharedPreferences,
  buildConfig: GeocoderBuildConfig,
) : Initializable {
  private val default = buildConfig.positionStackApiKey?.let(::PositionStackApiKey)
  private val apiKeyPrefPair = PrefPair<PositionStackApiKey?>(key = "positionStackApiKey", default = null)

  val apiKey: Preference<PositionStackApiKey?> = flowPrefs.getNullableObject(apiKeyPrefPair, ApiKeySerializer)

  override fun init() {
    if (apiKey.get()?.toString().isNullOrBlank()) {
      apiKey.set(default)
    }
  }

  private object ApiKeySerializer : SimpleNullableStringSerializer<PositionStackApiKey>(::PositionStackApiKey)
}
