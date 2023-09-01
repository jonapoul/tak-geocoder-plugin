@file:Suppress("UnstableApiUsage")

plugins {
  id("convention-android-lib")
  id("atak-takdev-plugin")
  id("convention-atak-sdk")
  id("convention-desugaring")
  id("convention-style")
  id("convention-test")
}

android {
  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementation(libs.alakazam.android.core)
  implementation(libs.alakazam.android.prefs)
  implementation(libs.alakazam.android.ui.core)
  implementation(libs.alakazam.kotlin.core)
  implementation(libs.alakazam.kotlin.time)
  implementation(libs.flowpreferences)
  implementation(libs.javax.inject)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
