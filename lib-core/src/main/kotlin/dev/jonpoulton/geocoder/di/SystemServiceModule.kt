package dev.jonpoulton.geocoder.di

import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import dev.jonpoulton.geocoder.core.AppContext
import org.koin.dsl.module

fun systemServiceModule(appContext: AppContext) = module {
  single { appContext.getSystemService<LocationManager>()!! }
  single { appContext.getSystemService<ConnectivityManager>()!! }
}
