package dev.jonpoulton.geocoder.test

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

inline fun <reified T : Any> buildApi(rule: MockWebServerRule): T {
  return Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .client(OkHttpClient())
    .baseUrl(rule.server.url(path = "/"))
    .build()
    .create(T::class.java)
}
