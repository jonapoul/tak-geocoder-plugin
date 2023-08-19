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
}
