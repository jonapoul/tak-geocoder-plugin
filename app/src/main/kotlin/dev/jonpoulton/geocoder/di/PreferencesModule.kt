@file:Suppress("DEPRECATION")

package dev.jonpoulton.geocoder.di

import android.preference.PreferenceManager
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import dev.jonpoulton.geocoder.core.AppContext
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val preferencesModule = module {
  factory { PreferenceManager.getDefaultSharedPreferences(get<AppContext>()) }
  factory { FlowSharedPreferences(get(), get<CoroutineDispatcher>(named(KoinDispatchers.IO))) }
}
