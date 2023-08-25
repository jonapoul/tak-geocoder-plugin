plugins {
  id("convention-android-lib")
  id("atak-takdev-plugin")
  id("convention-atak-sdk")
  id("convention-dagger")
  id("convention-style")
  id("convention-test")
  kotlin("plugin.serialization")
}

dependencies {
  implementation(project(":lib-http"))
  implementation(project(":lib-geocoder-core"))
  implementation(project(":lib-tak"))

  implementation(libs.alakazam.android.prefs)
  implementation(libs.flowpreferences)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.retrofit.core)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
