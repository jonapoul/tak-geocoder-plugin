plugins {
  id("convention-android-lib")
  id("atak-takdev-plugin")
  id("convention-atak-sdk")
  id("convention-style")
  id("convention-test")
  kotlin("plugin.serialization")
}

dependencies {
  implementation(project(":lib-settings"))

  implementation(libs.alakazam.android.core)
  implementation(libs.alakazam.kotlin.core)
  implementation(libs.flowpreferences)
  implementation(libs.javax.inject)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
