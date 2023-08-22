package dev.jonpoulton.geocoder.settings

import android.annotation.SuppressLint
import android.os.Bundle
import com.atakmap.android.preference.PluginPreferenceFragment
import com.atakmap.app.SettingsActivity
import dev.jonpoulton.geocoder.tak.PluginContext
import dev.jonpoulton.geocoder.tak.di.DaggerInjector
import timber.log.Timber

@Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")
@SuppressLint("ValidFragment")
class GeocoderSettingsFragment(
  pluginContext: PluginContext,
) : PluginPreferenceFragment(pluginContext, R.xml.settings, R.string.settings_summary) {

  private val viewModel by lazy {
    injector
      ?.vmFactory()
      ?.create(GeocoderSettingsViewModel::class.java)
      ?: error("Null injector!")
  }

  override fun getSummary(): String = pluginContext.getString(summaryID)

  override fun onCreate(savedInstanceState: Bundle?) {
    Timber.v("onCreate")
    super.onCreate(savedInstanceState)
    findPreference(KEY_ABOUT)?.setOnPreferenceClickListener { viewModel.showAboutDialog(activity) }
    findPreference(KEY_ADDRESS_LOOKUP)?.setOnPreferenceClickListener { goToAddressLookupSettings() }
  }

  private fun goToAddressLookupSettings(): Boolean {
    SettingsActivity.start("geocoderPreferences", "geocodeSupplier")
    return true
  }

  companion object {
    private var injector: DaggerInjector? = null

    fun initialise(injector: DaggerInjector) {
      this.injector = injector
    }

    const val KEY = "dev.jonpoulton.geocoder.settings.GeocoderSettingsFragment"

    private const val KEY_ADDRESS_LOOKUP = "GEOCODER_addressLookup"
    private const val KEY_ABOUT = "GEOCODER_about"
  }
}
