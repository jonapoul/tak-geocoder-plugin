package dev.jonpoulton.geocoder.settings

import android.app.Activity
import dev.jonpoulton.alakazam.core.IBuildConfig
import dev.jonpoulton.geocoder.tak.PluginContext
import dev.jonpoulton.geocoder.tak.ui.AboutDialog
import javax.inject.Inject

internal class ShowAboutDialogUseCase @Inject constructor(
  private val pluginContext: PluginContext,
  private val buildConfig: IBuildConfig,
) {
  fun show(activity: Activity) {
    AboutDialog.show(activity, pluginContext, buildConfig)
  }
}
