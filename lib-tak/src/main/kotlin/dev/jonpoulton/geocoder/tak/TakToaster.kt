package dev.jonpoulton.geocoder.tak

import android.widget.Toast
import androidx.annotation.StringRes
import dev.jonpoulton.alakazam.core.MainDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TakToaster @Inject constructor(
  private val appContext: AppContext,
  private val pluginContext: PluginContext,
  private val main: MainDispatcher,
) {
  fun toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(appContext, message, length).show()
  }

  fun toast(@StringRes message: Int, length: Int = Toast.LENGTH_LONG) {
    toast(pluginContext.getString(message), length)
  }

  suspend fun coToast(message: String, length: Int = Toast.LENGTH_LONG) {
    withContext(main) { Toast.makeText(appContext, message, length).show() }
  }

  suspend fun coToast(@StringRes message: Int, length: Int = Toast.LENGTH_LONG) {
    coToast(pluginContext.getString(message), length)
  }
}
