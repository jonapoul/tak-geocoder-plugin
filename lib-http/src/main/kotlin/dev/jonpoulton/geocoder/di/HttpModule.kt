package dev.jonpoulton.geocoder.di

import dagger.Module
import dagger.Provides
import dev.jonpoulton.alakazam.core.IBuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
class HttpModule {
  @Provides
  fun interceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor { message -> Timber.v(message) }
      .also { logger -> logger.level = HttpLoggingInterceptor.Level.BODY }
  }

  @Provides
  fun client(
    buildConfig: IBuildConfig,
    interceptor: HttpLoggingInterceptor
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .apply { if (buildConfig.debug) addInterceptor(interceptor) }
      .readTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS)
      .writeTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS)
      .connectTimeout(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS)
      .build()
  }

  @Provides
  fun retrofitBuilder(client: OkHttpClient): Retrofit.Builder {
    return Retrofit.Builder()
      .addConverterFactory(ScalarsConverterFactory.create())
      .client(client)
  }

  private companion object {
    private const val DEFAULT_TIMEOUT_SECS = 5L
  }
}
