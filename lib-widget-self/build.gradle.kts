plugins {
  id("convention-android-lib")
  id("atak-takdev-plugin")
  id("convention-atak-sdk")
  id("convention-style")
  id("convention-test")
}

dependencies {
  implementation(project(":lib-geocoder-core"))
  implementation(project(":lib-settings"))
  implementation(project(":lib-tak"))
  implementation(libs.alakazam.android.prefs)
  implementation(libs.alakazam.kotlin.core)
  implementation(libs.flowpreferences)
  implementation(libs.javax.inject)
  implementation(libs.timber)
}
