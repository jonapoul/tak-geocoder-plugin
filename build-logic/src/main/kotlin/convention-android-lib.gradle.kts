@file:Suppress("UnstableApiUsage")

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  id("kotlin-android")
  id("com.android.library")

  id("convention-kotlin")
}

val libs = the<LibrariesForLibs>()

android {
  compileSdk = libs.versions.sdk.compile.get().toInt()

  defaultConfig {
    minSdk = libs.versions.sdk.min.get().toInt()
    targetSdk = libs.versions.sdk.target.get().toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  buildFeatures {
    buildConfig = false
    viewBinding = false
  }

  lint {
    checkReleaseBuilds = false
    abortOnError = false
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
      isReturnDefaultValues = true
      all { it.jvmArgs("-noverify") }
    }
  }

  packagingOptions {
    resources.excludes.add("META-INF/*")
  }
}
