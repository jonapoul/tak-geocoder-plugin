plugins {
  id("convention-android-lib")
  id("convention-atak-sdk")
  id("convention-dagger")
  id("convention-style")
  id("convention-test")
  kotlin("plugin.serialization")
}

dependencies {
  implementation(project(":lib-core"))
  implementation(project(":lib-http"))
  implementation(project(":lib-geocoder-core"))

  implementation(libs.alakazam.android.prefs)
  implementation(libs.alakazam.tak.core)
  implementation(libs.flowpreferences)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.javax.inject)
  implementation(libs.retrofit.core)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
