plugins {
  id("convention-android-lib")
  id("convention-atak-sdk")
  id("convention-style")
  id("convention-test")

  kotlin("plugin.serialization")
}

dependencies {
  implementation(project(":lib-core"))

  implementation(libs.kotlinx.serialization.json)
  implementation(libs.okhttp.core)
  implementation(libs.okhttp.logging)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.scalars)

  testImplementation(project(":lib-test"))
}
