plugins {
  id("convention-android-lib")
  id("convention-atak-sdk")
  id("convention-style")
  id("convention-test")

  kotlin("plugin.serialization")
}

dependencies {
  implementation(project(":lib-core"))
  implementation(project(":lib-settings"))

  implementation(libs.flowpreferences)
  implementation(libs.kotlinx.serialization.json)

  testImplementation(project(":lib-test"))
}
