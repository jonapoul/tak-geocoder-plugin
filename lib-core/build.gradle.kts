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
  implementation(libs.koin.android)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
