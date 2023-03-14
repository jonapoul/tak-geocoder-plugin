package dev.jonpoulton.geocoder.test

fun <T : Any> T.getResourceJson(file: String): String {
  return this.javaClass
    .classLoader
    .getResourceAsStream(file)
    ?.bufferedReader()
    ?.use { it.readText() }
    ?: error("Failed to read from classloader")
}
