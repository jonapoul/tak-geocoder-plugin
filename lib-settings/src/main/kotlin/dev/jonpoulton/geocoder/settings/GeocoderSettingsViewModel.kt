package dev.jonpoulton.geocoder.settings

import android.app.Activity
import dev.jonpoulton.geocoder.tak.ui.TakViewModel
import javax.inject.Inject

class GeocoderSettingsViewModel @Inject internal constructor(
  private val aboutDialogUseCase: ShowAboutDialogUseCase,
) : TakViewModel() {
  fun showAboutDialog(activity: Activity): Boolean {
    aboutDialogUseCase.show(activity)
    return true
  }
}
