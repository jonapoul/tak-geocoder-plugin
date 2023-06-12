package dev.jonpoulton.geocoder.test

import org.koin.test.KoinTestRule
import retrofit2.Retrofit
import kotlin.reflect.KClass

fun <T : Any> buildApi(
  mockWebServerRule: MockWebServerRule,
  koinTestRule: KoinTestRule,
  apiClass: KClass<T>,
): T {
  return koinTestRule.koin
    .get<Retrofit.Builder>()
    .baseUrl(mockWebServerRule.server.url(path = "/"))
    .build()
    .create(apiClass.java)
}
