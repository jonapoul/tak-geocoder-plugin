package dev.jonpoulton.geocoder.test

import okhttp3.mockwebserver.MockResponse

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
