package dev.jonpoulton.geocoder.settings

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.Preference
import dev.jonpoulton.alakazam.prefs.PrefPair
import dev.jonpoulton.alakazam.prefs.getBoolean
import javax.inject.Inject

class PluginPreferences @Inject constructor(flowPrefs: FlowSharedPreferences) {
  val includeTag: Preference<Boolean> = flowPrefs.getBoolean(INCLUDE_TAG)

  companion object {
    val INCLUDE_TAG = PrefPair(key = "includeTag", default = false)
  }
}
