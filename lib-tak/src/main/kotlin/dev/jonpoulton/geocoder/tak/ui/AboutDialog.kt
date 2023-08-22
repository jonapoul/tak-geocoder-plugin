package dev.jonpoulton.geocoder.tak.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import com.atakmap.map.opengl.GLRenderGlobals.appContext
import dev.jonpoulton.alakazam.core.IBuildConfig
import dev.jonpoulton.alakazam.core.inflater
import dev.jonpoulton.alakazam.time.localisedFormatter
import dev.jonpoulton.alakazam.ui.core.show
import dev.jonpoulton.geocoder.tak.PluginContext
import dev.jonpoulton.geocoder.tak.R
import dev.jonpoulton.geocoder.tak.databinding.DialogAboutBinding

object AboutDialog {
  fun show(activity: Activity, pluginContext: PluginContext, buildConfig: IBuildConfig): AlertDialog {
    val binding = DialogAboutBinding.inflate(pluginContext.inflater).apply {
      version.icon.setImageResource(R.drawable.ic_version)
      version.title.setText(R.string.about_version)
      version.subtitle.text = buildConfig.versionName

      buildTime.icon.setImageResource(R.drawable.ic_calendar)
      buildTime.title.setText(R.string.about_build_time)
      val timestamp = "yyyy-MM-dd HH:mm:ss z".localisedFormatter.format(buildConfig.buildTime)
      buildTime.subtitle.text = timestamp

      source.icon.setImageResource(R.drawable.ic_source)
      source.title.setText(R.string.about_source)
      source.subtitle.text = buildConfig.repoName
      source.launchButton.show()
      source.launchButton.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(buildConfig.repoUrl)
        appContext.startActivity(intent)
      }
    }
    return PluginAlertDialogBuilder(activity, pluginContext)
      .setTitle(R.string.about_title)
      .setView(binding.root)
      .setSimplePositiveButton()
      .show()
  }
}
