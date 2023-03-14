package dev.jonpoulton.geocoder.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.view.isVisible
import com.atakmap.android.preference.PluginPreferenceFragment
import com.atakmap.app.SettingsActivity
import dev.jonpoulton.geocoder.core.AppContext
import dev.jonpoulton.geocoder.core.IBuildConfig
import dev.jonpoulton.geocoder.core.PluginAlertDialogBuilder
import dev.jonpoulton.geocoder.core.PluginContext
import dev.jonpoulton.geocoder.core.inflater
import dev.jonpoulton.geocoder.settings.databinding.DialogAboutBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")
@SuppressLint("ValidFragment")
class GeocoderSettingsFragment(
  private val myPluginContext: PluginContext,
) : PluginPreferenceFragment(myPluginContext, R.xml.settings, R.string.settings_summary), KoinComponent {

  private lateinit var appContext: AppContext
  private val buildConfig by inject<IBuildConfig>()

  override fun getSummary(): String = pluginContext.getString(summaryID)

  override fun onCreate(savedInstanceState: Bundle?) {
    Timber.v("showAboutDialog")
    super.onCreate(savedInstanceState)
    appContext = AppContext(activity)
    findPreference(KEY_ABOUT)?.setOnPreferenceClickListener { showAboutDialog() }
    findPreference(KEY_ADDRESS_LOOKUP)?.setOnPreferenceClickListener { goToAddressLookupSettings() }
  }

  private fun showAboutDialog(): Boolean {
    Timber.v("showAboutDialog")
    val binding = DialogAboutBinding.inflate(myPluginContext.inflater).apply {
      version.icon.setImageResource(R.drawable.ic_version)
      version.title.setText(R.string.about_version)
      version.subtitle.text = buildConfig.versionName

      buildTime.icon.setImageResource(R.drawable.ic_calendar)
      buildTime.title.setText(R.string.about_build_time)
      buildTime.subtitle.text = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss z")
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())
        .format(buildConfig.buildTime)

      source.icon.setImageResource(R.drawable.ic_source)
      source.title.setText(R.string.about_source)
      source.subtitle.setText(R.string.about_source_url)
      source.launchButton.isVisible = true
      source.launchButton.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(GITHUB_URL + myPluginContext.getString(R.string.about_source_url))
        appContext.startActivity(intent)
      }
    }

    /* Can't use the normal AppContext here because this is running in a separate Activity, so we can't show a dialog
    * without using that activity context */
    PluginAlertDialogBuilder(appContext, myPluginContext)
      .setTitle(R.string.app_name)
      .setView(binding.root)
      .setSimplePositiveButton()
      .show()
    return true
  }

  private fun goToAddressLookupSettings(): Boolean {
    SettingsActivity.start("geocoderPreferences", "geocodeSupplier")
    return true
  }

  companion object {
    const val KEY = "dev.jonpoulton.geocoder.settings.GeocoderSettingsFragment"

    private const val KEY_ADDRESS_LOOKUP = "GEOCODER_addressLookup"
    private const val KEY_ABOUT = "GEOCODER_about"
    private const val GITHUB_URL = "https://github.com/"
  }
}
