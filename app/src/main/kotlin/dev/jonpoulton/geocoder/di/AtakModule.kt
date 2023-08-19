package dev.jonpoulton.geocoder.di

import com.atakmap.android.user.geocode.GeocodeManager
import dagger.Module
import dagger.Provides
import dev.jonpoulton.alakazam.tak.core.AppContext

@Module
internal class AtakModule {
  @Provides
  fun geocodeManager(appContext: AppContext): GeocodeManager = GeocodeManager.getInstance(appContext)
}
