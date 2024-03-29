package dev.jonpoulton.geocoder.di

import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import dagger.Module
import dagger.Provides
import dev.jonpoulton.geocoder.tak.AppContext

@Module
internal class SystemServiceModule {
  @Provides
  fun connectivityManager(appContext: AppContext): ConnectivityManager = appContext.getSystemService()!!
}
