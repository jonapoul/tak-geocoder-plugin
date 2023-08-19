package dev.jonpoulton.geocoder.w3w

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.Preference
import dev.jonpoulton.alakazam.prefs.PrefPair
import dev.jonpoulton.alakazam.prefs.SimpleNullableStringSerializer
import dev.jonpoulton.alakazam.prefs.getNullableObject
import dev.jonpoulton.geocoder.core.GeocoderBuildConfig
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.w3w.model.WhatThreeWordsApiKey
import javax.inject.Inject

internal class WhatThreeWordsPreferences @Inject constructor(
  flowPrefs: FlowSharedPreferences,
  buildConfig: GeocoderBuildConfig,
) : Initializable {
  private val default = buildConfig.w3wApiKey?.let(::WhatThreeWordsApiKey)
  private val apiKeyPrefPair = PrefPair<WhatThreeWordsApiKey?>(key = "w3wApiKey", default = null)

  val apiKey: Preference<WhatThreeWordsApiKey?> = flowPrefs.getNullableObject(apiKeyPrefPair, ApiKeySerializer)

  override fun init() {
    if (apiKey.get()?.toString().isNullOrBlank()) {
      apiKey.set(default)
    }
  }

  private object ApiKeySerializer : SimpleNullableStringSerializer<WhatThreeWordsApiKey>(::WhatThreeWordsApiKey)
}
