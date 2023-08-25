plugins {
  id("convention-android-lib")
  id("atak-takdev-plugin")
  id("convention-atak-sdk")
  id("convention-dagger")
  id("convention-style")
  id("convention-test")
  kotlin("plugin.serialization")
}

dependencies {
  implementation(libs.alakazam.android.core)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.okhttp.core)
  implementation(libs.okhttp.logging)
  api(libs.retrofit.core)
  implementation(libs.retrofit.scalars)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
