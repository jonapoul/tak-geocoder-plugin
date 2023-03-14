package dev.jonpoulton.geocoder.w3w

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.NullableSerializer
import com.fredporciuncula.flow.preferences.Preference
import dev.jonpoulton.geocoder.core.IBuildConfig
import dev.jonpoulton.geocoder.core.PrefPair
import dev.jonpoulton.geocoder.core.getNullableObject
import dev.jonpoulton.geocoder.geocoding.Initializable
import dev.jonpoulton.geocoder.w3w.model.WhatThreeWordsApiKey

internal class WhatThreeWordsPreferences(
  flowPrefs: FlowSharedPreferences,
  buildConfig: IBuildConfig,
) : Initializable {
  private val default = buildConfig.w3wApiKey?.let(::WhatThreeWordsApiKey)
  private val apiKeyPrefPair = PrefPair<WhatThreeWordsApiKey?>(key = "w3wApiKey", default = null)

  val apiKey: Preference<WhatThreeWordsApiKey?> = flowPrefs.getNullableObject(apiKeyPrefPair, ApiKeySerializer)

  init {
    init()
  }

  override fun init() {
    if (apiKey.get()?.toString().isNullOrBlank()) {
      apiKey.set(default)
    }
  }

  private object ApiKeySerializer : NullableSerializer<WhatThreeWordsApiKey> {
    override fun deserialize(serialized: String?) = serialized?.let(::WhatThreeWordsApiKey)
    override fun serialize(value: WhatThreeWordsApiKey?) = value?.toString()
  }
}
