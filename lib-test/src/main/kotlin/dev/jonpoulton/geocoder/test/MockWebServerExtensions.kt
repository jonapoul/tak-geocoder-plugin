package dev.jonpoulton.geocoder.test

import mockwebserver3.MockResponse
import mockwebserver3.junit4.MockWebServerRule

fun MockWebServerRule.enqueue(
  body: String?,
  code: Int = 200,
) {
  server.enqueue(
    MockResponse().apply {
      setResponseCode(code)
      body?.let(::setBody)
    },
  )
}
