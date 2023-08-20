package dev.jonpoulton.geocoder.http.di

import dagger.Module
import dagger.Provides
import dev.jonpoulton.alakazam.core.IBuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
class HttpModule {
  @Provides
  fun interceptor(): Interceptor {
    return HttpLoggingInterceptor { message -> Timber.v(message) }
      .also { logger -> logger.level = HttpLoggingInterceptor.Level.BODY }
  }

  @Provides
  fun retrofitBuilder(buildConfig: IBuildConfig, interceptor: Interceptor): Retrofit.Builder {
    val client = OkHttpClient.Builder()
      .apply { if (buildConfig.debug) addInterceptor(interceptor) }
      .readTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS)
      .writeTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS)
      .connectTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS)
      .build()
    return Retrofit.Builder()
      .addConverterFactory(ScalarsConverterFactory.create())
      .client(client)
  }

  private companion object {
    private const val DEFAULT_TIMEOUT_SECS = 5L
  }
}
