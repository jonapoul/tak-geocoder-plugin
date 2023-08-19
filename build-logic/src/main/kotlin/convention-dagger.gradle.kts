@file:Suppress("UnstableApiUsage")

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  kotlin("android")
  kotlin("kapt")
}

val libs = the<LibrariesForLibs>()
val implementation by configurations
val kapt by configurations

dependencies {
  implementation(libs.dagger.core)
  kapt(libs.dagger.compiler)
}
