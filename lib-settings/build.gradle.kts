@file:Suppress("UnstableApiUsage")

plugins {
  id("convention-android-lib")
  id("convention-atak-sdk")
  id("convention-dagger")
  id("convention-desugaring")
  id("convention-style")
  id("convention-test")
}

dependencies {
  implementation(project(":lib-core"))
  implementation(project(":lib-tak"))

  implementation(libs.alakazam.android.prefs)
  implementation(libs.flowpreferences)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
