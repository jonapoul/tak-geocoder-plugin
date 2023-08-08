plugins {
  kotlin("jvm")
  id("convention-kotlin")
  id("convention-style")
}

dependencies {
  api(libs.test.junit)
  api(libs.test.koin.junit4)
  api(libs.test.kotlin.common)
  api(libs.test.kotlin.coroutines)
  api(libs.test.kotlin.junit)
  api(libs.test.okhttp.webserver)
  api(libs.test.timber)
  api(libs.test.turbine)

  implementation(libs.okhttp.core)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.scalars)
  implementation(libs.koin.core)
  implementation(libs.kotlinx.serialization.json)
}
