@file:Suppress("UnstableApiUsage")

import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "11"
    freeCompilerArgs += listOf(
      "-Xjvm-default=all-compatibility",
      "-opt-in=kotlin.RequiresOptIn",
    )
  }
}

val libs = the<LibrariesForLibs>()
val implementation by configurations

dependencies {
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlin.coroutines.core)
}
