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
  implementation(project(":lib-core"))

  implementation(libs.flowpreferences)

  testImplementation(project(":lib-test"))
}
