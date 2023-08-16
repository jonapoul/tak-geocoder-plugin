package dev.jonpoulton.geocoder.di

import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import dev.jonpoulton.geocoder.core.AppContext
import org.koin.dsl.module

fun systemServiceModule(appContext: AppContext) = module {
  single { appContext.getSystemService<LocationManager>() ?: fail() }
  single { appContext.getSystemService<ConnectivityManager>() ?: fail() }
}

private inline fun <reified T> fail(): T? = error("Null service ${T::class.java.canonicalName}")
