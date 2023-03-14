package dev.jonpoulton.geocoder.plugin

import timber.log.Timber

class GeocoderDebugTree : Timber.DebugTree() {
  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    super.log(priority, tag, message = "GEOCODER: $message", t)
  }

  override fun createStackElementTag(element: StackTraceElement): String =
    "(${element.fileName}:${element.lineNumber})#${element.methodName}"
}
