package dev.jonpoulton.geocoder.core

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

val PluginContext.inflater: LayoutInflater
  get() = LayoutInflater.from(this)

/**
 * MUST be called with the app context, not the plugin context!!
 */
fun AppContext.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
  Toast.makeText(this, message, length).show()
}

fun PluginContext.getCompatDrawable(@DrawableRes drawableRes: Int): Drawable? =
  ContextCompat.getDrawable(this, drawableRes)
