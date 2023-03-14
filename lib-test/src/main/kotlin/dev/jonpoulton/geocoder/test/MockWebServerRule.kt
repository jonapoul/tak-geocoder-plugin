package dev.jonpoulton.geocoder.test

import mockwebserver3.MockWebServer
import mockwebserver3.junit4.MockWebServerRule
import org.junit.rules.ExternalResource
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Copied from OkHttp's MockWebServerRule class in v5.0.0-alpha.11, but this brings in a transitive Kotlin 1.7.10
 * dependency so we need an older version of the library.
 */
class MockWebServerRule : ExternalResource() {
  val server: MockWebServer = MockWebServer()

  override fun before() {
    try {
      server.start()
    } catch (e: IOException) {
      throw RuntimeException(e)
    }
  }

  override fun after() {
    try {
      server.shutdown()
    } catch (e: IOException) {
      logger.log(Level.WARNING, "MockWebServer shutdown failed", e)
    }
  }

  companion object {
    private val logger = Logger.getLogger(MockWebServerRule::class.java.name)
  }
}
