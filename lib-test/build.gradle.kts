plugins {
  kotlin("jvm")
  id("convention-kotlin")
  id("convention-style")
  id("convention-test")
}

dependencies {
  implementation(libs.koin.core)
  implementation(libs.okhttp.core)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.scalars)
  implementation(libs.kotlinx.serialization.json)

  api(libs.test.junit)
  api(libs.test.koin.junit4)
  api(libs.test.kotlin.common)
  api(libs.test.kotlin.coroutines)
  api(libs.test.kotlin.junit)
  api(libs.test.okhttp.webserver)
  api(libs.test.timber)
  api(libs.test.turbine)
}
