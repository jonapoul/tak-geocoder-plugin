plugins {
  id("convention-android-lib")
  id("convention-atak-sdk")
  id("convention-style")
  id("convention-test")

  id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
  implementation(project(":lib-core"))

  implementation(libs.koin.android)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.okhttp.core)
  implementation(libs.okhttp.logging)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.scalars)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
