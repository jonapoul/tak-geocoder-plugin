@file:Suppress("UnstableApiUsage", "SwallowedException")

import com.android.build.api.dsl.DefaultConfig
import com.atakmap.gradle.takdev.TakDevPlugin

plugins {
  kotlin("android")
  kotlin("kapt")
  id("com.android.application")
  id("atak-takdev-plugin")
  id("convention-atak-sdk")
  id("convention-dagger")
  id("convention-desugaring")
  id("convention-kotlin")
  id("convention-test")
}

android {
  compileSdk = libs.versions.sdk.compile.get().toInt()

  defaultConfig {
    minSdk = libs.versions.sdk.min.get().toInt()
    targetSdk = libs.versions.sdk.target.get().toInt()

    insertBuildConfigFromLocalProps(key = "W3W_API_KEY")
    insertBuildConfigFromLocalProps(key = "POSITIONSTACK_API_KEY")
    insertBuildConfigFromLocalProps(key = "MAPQUEST_API_KEY")

    buildConfigField(
      type = "String",
      name = "GIT_HASH",
      value = "\"${getVersionName()}\"",
    )

    buildConfigField(
      type = "java.time.Instant",
      name = "BUILD_TIME",
      value = "java.time.Instant.ofEpochMilli(${System.currentTimeMillis()}L)",
    )
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

  signingConfigs {
    getByName("debug") {
      val kf = getFromLocalProperties(key = "takDebugKeyFile")
      val kfp = getFromLocalProperties(key = "takDebugKeyFilePassword")
      val ka = getFromLocalProperties(key = "takDebugKeyAlias")
      val kp = getFromLocalProperties(key = "takDebugKeyPassword")

      if (kf == null) throw GradleException("No signing key configured!")
      val file = File(rootProject.projectDir, kf)
      if (!file.exists()) throw GradleException("File $file doesn't exist")

      storeFile = file
      if (kfp != null) storePassword = kfp
      if (ka != null) keyAlias = ka
      if (kp != null) keyPassword = kp
    }
    create("release") {
      val kf = getFromLocalProperties(key = "takReleaseKeyFile")
      val kfp = getFromLocalProperties(key = "takReleaseKeyFilePassword")
      val ka = getFromLocalProperties(key = "takReleaseKeyAlias")
      val kp = getFromLocalProperties(key = "takReleaseKeyPassword")

      if (kf == null) throw GradleException("No signing key configured!")
      val file = File(rootProject.projectDir, kf)
      if (!file.exists()) throw GradleException("File $file doesn't exist")

      storeFile = file
      if (kfp != null) storePassword = kfp
      if (ka != null) keyAlias = ka
      if (kp != null) keyPassword = kp
    }
  }

  buildTypes {
    debug {
      isDebuggable = true
      matchingFallbacks.add("sdk")
      signingConfig = signingConfigs.getByName("debug")
    }

    release {
      isDebuggable = true
      matchingFallbacks.add("odk")
      isMinifyEnabled = true
      signingConfig = signingConfigs.getByName("release")
      if (isPipeline) {
        proguardFiles("proguard-rules.pro", "proguard-mapping.pro")
      } else {
        proguardFile("proguard-rules.pro")
      }
    }
  }

  packagingOptions {
    resources.excludes.addAll(
      listOf(
        "META-INF/DEPENDENCIES",
        "META-INF/INDEX.LIST",
      ),
    )
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  buildFeatures {
    buildConfig = true
    viewBinding = false
  }

  val pluginVersion = libs.versions.pluginVersionName.get()
  val atakVersion = libs.versions.atak.get()

  sourceSets {
    getByName("main") {
      java.srcDirs("src/main/kotlin")
      setProperty("archivesBaseName", "ATAK-Plugin-geocoder-${pluginVersion}-${getVersionName()}-${atakVersion}")
      defaultConfig.versionCode = getVersionCode()
      defaultConfig.versionName = "$pluginVersion (${getVersionName()}) - [$atakVersion]"
    }

    getByName("debug").setRoot("build-types/debug")
    getByName("release").setRoot("build-types/release")
  }

  flavorDimensions += "application"
  productFlavors {
    create("civ") {
      dimension = "application"
      manifestPlaceholders["atakApiVersion"] = "com.atakmap.app@${atakVersion}.CIV"
    }
  }
}

dependencies {
  listOf(
    ":lib-core",
    ":lib-geocoder-core",
    ":lib-geocoder-mapquest",
    ":lib-geocoder-positionstack",
    ":lib-geocoder-what3words",
    ":lib-settings",
  ).map(::project).forEach(::implementation)

  implementation(libs.alakazam.android.core)
  implementation(libs.alakazam.kotlin.core)
  implementation(libs.alakazam.kotlin.time)
  implementation(libs.alakazam.tak.core)
  implementation(libs.alakazam.tak.dagger)
  implementation(libs.alakazam.tak.plugin)
  implementation(libs.alakazam.tak.ui)

  implementation(libs.flowpreferences)
  implementation(libs.timber)

  testImplementation(project(":lib-test"))
}

fun DefaultConfig.insertBuildConfigFromLocalProps(key: String) {
  val value = getProperty(key, defaultValue = null)
  if (value == null) {
    logger.warn("$key wasn't found in local.properties, this feature will be disabled in the plugin")
    buildConfigField(type = "String", name = key, value = "null")
  } else {
    buildConfigField(type = "String", name = key, value = "\"${value}\"")
  }
}

fun getVersionName(): String {
  return try {
    val version = TakDevPlugin.getGitInfo(project, listOf("rev-parse", "--short=8", "HEAD"))
    println("versionName[git]: $version")
    version
  } catch (e: Exception) {
    println("error occured, using version of $version")
    "1"
  }
}

// Attempt to get a suitable version code for the plugin based on
// either a git or svn repository
fun getVersionCode(): Int {
  return try {
    val outputAsString = TakDevPlugin.getGitInfo(project, listOf("show", "-s", "--format=%ct"))
    val revision = outputAsString.toInt()
    println("version[git]: $revision")
    revision
  } catch (e: Exception) {
    println("error occured, using default revision")
    1
  }
}
