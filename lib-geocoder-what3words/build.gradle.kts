plugins {
  id("convention-android-lib")
  id("convention-atak-sdk")
  id("convention-style")

  id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
  implementation(project(":lib-core"))
  api(project(":lib-http"))
  api(project(":lib-geocoder-core"))

  implementation(libs.flowpreferences)
  implementation(libs.koin.android)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.retrofit.core)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}
