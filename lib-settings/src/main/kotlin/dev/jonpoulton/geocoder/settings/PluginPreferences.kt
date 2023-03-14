package dev.jonpoulton.geocoder.settings

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.Preference
import dev.jonpoulton.geocoder.core.PrefPair
import dev.jonpoulton.geocoder.core.getBoolean

class PluginPreferences(flowPrefs: FlowSharedPreferences) {
  val includeTag: Preference<Boolean> = flowPrefs.getBoolean(INCLUDE_TAG)

  companion object {
    val INCLUDE_TAG = PrefPair(key = "includeTag", default = false)
  }
}
