package dev.jonpoulton.geocoder.tak.ui

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.atakmap.android.maps.MapView
import dev.jonpoulton.alakazam.core.getCompatDrawable
import dev.jonpoulton.alakazam.core.inflater
import dev.jonpoulton.geocoder.tak.AppContext
import dev.jonpoulton.geocoder.tak.PluginContext

class PluginAlertDialogBuilder : AlertDialog.Builder {
  private val pluginContext: PluginContext

  constructor(appContext: Context, pluginContext: PluginContext) : super(appContext) {
    this.pluginContext = pluginContext
  }

  constructor(appContext: AppContext, pluginContext: PluginContext) : super(appContext) {
    this.pluginContext = pluginContext
  }

  constructor(mapView: MapView, pluginContext: PluginContext) : super(mapView.context) {
    this.pluginContext = pluginContext
  }

  override fun setView(@LayoutRes layoutResId: Int): PluginAlertDialogBuilder =
    super.setView(pluginContext.inflater.inflate(layoutResId, null, false)) as PluginAlertDialogBuilder

  override fun setView(view: View?): PluginAlertDialogBuilder =
    super.setView(view) as PluginAlertDialogBuilder

  override fun setTitle(@StringRes titleId: Int): PluginAlertDialogBuilder =
    super.setTitle(pluginContext.getString(titleId)) as PluginAlertDialogBuilder

  override fun setMessage(@StringRes messageId: Int): PluginAlertDialogBuilder =
    super.setMessage(pluginContext.getString(messageId)) as PluginAlertDialogBuilder

  override fun setMessage(message: CharSequence): PluginAlertDialogBuilder =
    super.setMessage(message) as PluginAlertDialogBuilder

  override fun setIcon(@DrawableRes iconId: Int): PluginAlertDialogBuilder =
    super.setIcon(pluginContext.getCompatDrawable(iconId)) as PluginAlertDialogBuilder

  fun setSimplePositiveButton(
    @StringRes text: Int = android.R.string.ok,
    onClick: (() -> Unit)? = null,
  ): PluginAlertDialogBuilder {
    val string = pluginContext.getString(text)
    return if (onClick == null) {
      setPositiveButton(string, null) as PluginAlertDialogBuilder
    } else {
      setPositiveButton(string) { _, _ -> onClick.invoke() } as PluginAlertDialogBuilder
    }
  }

  fun setSimpleNegativeButton(
    @StringRes text: Int = android.R.string.cancel,
    onClick: (() -> Unit)? = null,
  ): PluginAlertDialogBuilder {
    val string = pluginContext.getString(text)
    return if (onClick == null) {
      setNegativeButton(string, null) as PluginAlertDialogBuilder
    } else {
      setNegativeButton(string) { _, _ -> onClick.invoke() } as PluginAlertDialogBuilder
    }
  }

  fun setSimpleNeutralButton(
    @StringRes text: Int,
    onClick: (() -> Unit)? = null,
  ): PluginAlertDialogBuilder {
    val string = pluginContext.getString(text)
    return if (onClick == null) {
      setNeutralButton(string, null) as PluginAlertDialogBuilder
    } else {
      setNeutralButton(string) { _, _ -> onClick.invoke() } as PluginAlertDialogBuilder
    }
  }
}
