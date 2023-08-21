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
  implementation(libs.alakazam.android.core)
  implementation(libs.alakazam.android.prefs)
  implementation(libs.alakazam.android.ui.core)
  implementation(libs.alakazam.kotlin.core)
  implementation(libs.alakazam.kotlin.time)
  implementation(libs.androidx.fragmentKtx)
  implementation(libs.androidx.recyclerview)
  implementation(libs.flowpreferences)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
