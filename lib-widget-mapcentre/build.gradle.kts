plugins {
  id("convention-android-lib")
  id("convention-atak-sdk")
  id("convention-style")
  id("convention-test")
}

dependencies {
  implementation(project(":lib-geocoder-core"))
  implementation(libs.alakazam.android.prefs)
  implementation(libs.alakazam.kotlin.core)
  implementation(libs.alakazam.tak.core)
  implementation(libs.alakazam.tak.dagger)
  implementation(libs.alakazam.tak.ui)
  implementation(libs.flowpreferences)
  implementation(libs.javax.inject)
  implementation(libs.timber)
}
