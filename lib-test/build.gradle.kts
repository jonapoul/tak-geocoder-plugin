plugins {
  kotlin("jvm")
  id("convention-kotlin")
  id("convention-style")
  id("convention-test")
}

dependencies {
  implementation(libs.okhttp.core)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.scalars)
  implementation(libs.koin.core)
  implementation(libs.kotlinx.serialization.json)
}
