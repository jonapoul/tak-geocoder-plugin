package dev.jonpoulton.geocoder.core

import android.net.ConnectivityManager

@Suppress("DEPRECATION")
fun ConnectivityManager.isNetworkAvailable(): Boolean {
  val info = this.activeNetworkInfo
  return info != null && info.isConnected
}
