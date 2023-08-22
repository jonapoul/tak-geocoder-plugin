@file:Suppress("DEPRECATION")

package dev.jonpoulton.geocoder.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import dagger.Module
import dagger.Provides
import dev.jonpoulton.alakazam.core.IODispatcher
import dev.jonpoulton.geocoder.tak.AppContext
import javax.inject.Singleton

@Module
class PreferencesModule {
  @Provides
  @Singleton
  fun sharedPrefs(appContext: AppContext): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(appContext)
  }

  @Provides
  @Singleton
  fun flowPrefs(prefs: SharedPreferences, io: IODispatcher): FlowSharedPreferences {
    return FlowSharedPreferences(prefs, io)
  }
}
