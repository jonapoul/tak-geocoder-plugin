@file:Suppress("UnstableApiUsage")

plugins {
  id("convention-android-lib")
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
  implementation(libs.flowpreferences)
  api(libs.koin.android)
  api(libs.timber)

  testImplementation(project(":lib-test"))
}
