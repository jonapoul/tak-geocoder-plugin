package dev.jonpoulton.geocoder.widget.centre

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.Preference
import dev.jonpoulton.alakazam.prefs.PrefPair
import dev.jonpoulton.alakazam.prefs.getBoolean
import javax.inject.Inject

class MapCentrePreferences @Inject constructor(flowPrefs: FlowSharedPreferences) {
  val designateMapCentre: Preference<Boolean> = flowPrefs.getBoolean(DESIGNATE_MAP_CENTRE)

  private companion object {
    /* Defined in ATAK, we're just hooking into its value */
    val DESIGNATE_MAP_CENTRE = PrefPair(key = "map_center_designator", default = false)
  }
}
