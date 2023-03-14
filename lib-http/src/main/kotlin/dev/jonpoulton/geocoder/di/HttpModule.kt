package dev.jonpoulton.geocoder.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.jonpoulton.geocoder.core.IBuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val DEFAULT_TIMEOUT_SECS = 5L

val httpModule = module {
  factory {
    HttpLoggingInterceptor { message -> Timber.v(message) }
      .also { it.level = HttpLoggingInterceptor.Level.BODY }
  }

  single {
    val buildConfig = get<IBuildConfig>()
    OkHttpClient.Builder()
      .apply { if (buildConfig.isDebug) addInterceptor(get<HttpLoggingInterceptor>()) }
      .readTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS)
      .writeTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS)
      .connectTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS)
      .build()
  }

  single {
    Retrofit.Builder()
      .addConverterFactory(LenientJson.asConverterFactory("application/json".toMediaType()))
      .client(get())
  }
}
