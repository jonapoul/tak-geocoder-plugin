plugins {
  id("convention-android-lib")
  id("convention-atak-sdk")
  id("convention-style")
  id("convention-test")

  kotlin("plugin.serialization")
}

dependencies {
  implementation(project(":lib-core"))
  api(project(":lib-http"))
  api(project(":lib-geocoder-core"))

  implementation(libs.flowpreferences)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.retrofit.core)

  testImplementation(project(":lib-test"))
}
