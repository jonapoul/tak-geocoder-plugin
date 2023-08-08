package dev.jonpoulton.geocoder.core

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.atakmap.android.maps.MapView

class PluginAlertDialogBuilder : AlertDialog.Builder {
  private val pluginContext: PluginContext

  constructor(
    appContext: AppContext,
    pluginContext: PluginContext,
  ) : super(appContext) {
    this.pluginContext = pluginContext
  }

  constructor(
    mapView: MapView,
    pluginContext: PluginContext,
  ) : super(mapView.context) {
    this.pluginContext = pluginContext
  }

  override fun setTitle(@StringRes titleId: Int): PluginAlertDialogBuilder =
    super.setTitle(pluginContext.getString(titleId)) as PluginAlertDialogBuilder

  fun setTitle(title: String): PluginAlertDialogBuilder =
    super.setTitle(title) as PluginAlertDialogBuilder

  override fun setMessage(@StringRes messageId: Int): PluginAlertDialogBuilder =
    super.setMessage(pluginContext.getString(messageId)) as PluginAlertDialogBuilder

  override fun setMessage(message: CharSequence?): PluginAlertDialogBuilder =
    super.setMessage(message) as PluginAlertDialogBuilder

  override fun setView(view: View): PluginAlertDialogBuilder =
    super.setView(view) as PluginAlertDialogBuilder

  override fun setSingleChoiceItems(
    @ArrayRes itemsId: Int,
    checkedItem: Int,
    listener: DialogInterface.OnClickListener?,
  ): PluginAlertDialogBuilder =
    super.setSingleChoiceItems(
      pluginContext.resources.getStringArray(itemsId),
      checkedItem,
      listener,
    ) as PluginAlertDialogBuilder

  override fun setSingleChoiceItems(
    items: Array<out CharSequence>?,
    checkedItem: Int,
    listener: DialogInterface.OnClickListener?,
  ): PluginAlertDialogBuilder =
    super.setSingleChoiceItems(items, checkedItem, listener) as PluginAlertDialogBuilder

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
}
