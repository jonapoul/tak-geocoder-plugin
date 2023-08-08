@file:Suppress("UnstableApiUsage", "SwallowedException")

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    maven { url = uri("https://jitpack.io") }
    mavenLocal()

    data class RepoProperties(
      val takrepoUrl: String?,
      val takrepoUser: String?,
      val takrepoPassword: String?,
      val isPipeline: Boolean,
    )

    fun groovy.lang.GroovyObject.getNullableStringProperty(key: String): String? = try {
      val value = getProperty(key)
      value as? String ?: error("Expected string, got $value")
    } catch (e: groovy.lang.MissingPropertyException) {
      null
    }

    val repoProperties = settings.withGroovyBuilder {
      RepoProperties(
        takrepoUrl = getNullableStringProperty(key = "takrepoUrl"),
        takrepoUser = getNullableStringProperty(key = "takrepoUser"),
        takrepoPassword = getNullableStringProperty(key = "takrepoPassword"),
        isPipeline = getNullableStringProperty(key = "takrepo.url") != null,
      )
    }
    println("Read properties $repoProperties")

    if (repoProperties.isPipeline && repoProperties.takrepoUrl != null) {
      println("Using pipeline, declaring takRepo maven repo")
      maven {
        isAllowInsecureProtocol = true
        url = uri(repoProperties.takrepoUrl)
        credentials {
          username = repoProperties.takrepoUser
          password = repoProperties.takrepoPassword
        }
      }
    } else {
      maven { url = uri("${rootProject.projectDir}/maven") }
    }
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
    google()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("${rootProject.projectDir}/maven") }
  }
}

includeBuild("build-logic")

include(":app")
include(":lib-core")
include(":lib-geocoder-core")
include(":lib-geocoder-mapquest")
include(":lib-geocoder-positionstack")
include(":lib-geocoder-what3words")
include(":lib-http")
include(":lib-settings")
include(":lib-test")

// https://docs.gradle.org/current/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// https://docs.gradle.org/current/userguide/configuration_cache.html#config_cache:stable
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
