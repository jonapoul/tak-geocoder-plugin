@file:Suppress("UnstableApiUsage")

plugins {
  id("convention-android-lib")
  id("atak-takdev-plugin")
  id("convention-atak-sdk")
  id("convention-desugaring")
  id("convention-style")
  id("convention-test")
}

dependencies {
  implementation(project(":lib-tak"))

  implementation(libs.alakazam.android.prefs)
  implementation(libs.flowpreferences)
  implementation(libs.javax.inject)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
