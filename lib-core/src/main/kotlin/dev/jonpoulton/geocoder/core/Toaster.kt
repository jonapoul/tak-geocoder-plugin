package dev.jonpoulton.geocoder.core

import android.widget.Toast
import androidx.annotation.StringRes
import kotlinx.coroutines.withContext

class Toaster(
  private val appContext: AppContext,
  private val pluginContext: PluginContext,
  private val main: MainDispatcher,
) {
  fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(appContext, message, length).show()
  }

  fun toast(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) {
    toast(pluginContext.getString(message), length)
  }

  suspend fun coToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    withContext(main) { Toast.makeText(appContext, message, length).show() }
  }

  suspend fun coToast(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) {
    coToast(pluginContext.getString(message), length)
  }
}
