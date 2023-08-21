@file:Suppress("UnstableApiUsage")

plugins {
  id("convention-android-lib")
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
  implementation(libs.flowpreferences)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
