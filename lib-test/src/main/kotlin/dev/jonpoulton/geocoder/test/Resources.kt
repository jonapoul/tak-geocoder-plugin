package dev.jonpoulton.geocoder.test

import  dev.jonpoulton.alakazam.test.getResourceAsStream

inline fun <reified T> T.getResourceJson(file: String): String {
  return this.getResourceAsStream(file)
    .bufferedReader()
    .use { it.readText() }
}
