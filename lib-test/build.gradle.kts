plugins {
  kotlin("jvm")
  id("convention-kotlin")
  id("convention-style")
}

dependencies {
  implementation(libs.okhttp.core)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.scalars)
  implementation(libs.kotlinx.serialization.json)

  implementation(libs.test.junit)
  implementation(libs.test.kotlin.common)
  implementation(libs.test.kotlin.coroutines)
  implementation(libs.test.kotlin.junit)
  implementation(libs.test.okhttp.webserver)
  implementation(libs.test.timber)
  implementation(libs.test.turbine)
}
