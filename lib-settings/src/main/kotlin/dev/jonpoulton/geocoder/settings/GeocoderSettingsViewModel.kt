package dev.jonpoulton.geocoder.settings

import dev.jonpoulton.alakazam.tak.domain.ShowAboutDialogUseCase
import dev.jonpoulton.alakazam.tak.ui.TakViewModel
import javax.inject.Inject

class GeocoderSettingsViewModel @Inject constructor(
  private val aboutDialogUseCase: ShowAboutDialogUseCase,
) : TakViewModel() {
  fun showAboutDialog(): Boolean {
    aboutDialogUseCase.show()
    return true
  }
}
