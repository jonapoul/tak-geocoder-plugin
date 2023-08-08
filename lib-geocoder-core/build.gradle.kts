plugins {
  id("convention-android-lib")
  id("convention-atak-sdk")
  id("convention-style")

  id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
  implementation(project(":lib-core"))
  implementation(project(":lib-settings"))

  implementation(libs.koin.android)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
