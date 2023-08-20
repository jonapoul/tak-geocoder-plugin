buildscript {
  val isPipeline = properties["takrepo.url"] != null

  extra.apply {
    set("takrepoUrl", project.properties["takrepo.url"] ?: "http://localhost/")
    set("takrepoUser", project.properties["takrepo.user"] ?: "invalid")
    set("takrepoPassword", project.properties["takrepo.password"] ?: "invalid")
    set("takdevPlugin", project.properties["takrepo.plugin"] ?: "${rootDir}/../../atak-gradle-takdev.jar")
    set("takdevConTestEnable", false)
    set("ATAK_VERSION", libs.versions.atak.get())
    set("PLUGIN_VERSION", libs.versions.pluginVersionName.get())
  }
  println("Extras = ${extra.properties}")

  fun Project.getStringProperty(key: String): String {
    val value = project.properties[key]
    return value as? String ?: error("Expected string from $key, got $value")
  }

  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("${rootProject.projectDir}/../maven") }

    if (isPipeline) {
      println("Using pipeline, declaring takRepo maven repo")
      val takrepoUrl = getStringProperty(key = "takrepoUrl")
      val takrepoUser = getStringProperty(key = "takrepoUser")
      val takrepoPassword = getStringProperty(key = "takrepoPassword")
      maven {
        isAllowInsecureProtocol = true
        url = uri(takrepoUrl)
        credentials {
          username = takrepoUser
          password = takrepoPassword
        }
      }
    }
  }

  dependencies {
    classpath(libs.plugin.agp)
    classpath(libs.plugin.detekt)
    classpath(libs.plugin.doctor)
    classpath(libs.plugin.kotlin)
    classpath(libs.plugin.kover)
    classpath(libs.plugin.ktlint)
    classpath(libs.plugin.licensee)
    classpath(libs.plugin.serialization)
    classpath(libs.plugin.spotless)

    if (isPipeline) {
      /* Full-fat plugin */
      classpath("com.atakmap.gradle:atak-gradle-takdev:2.+")
    } else {
      /* Abridged plugin which skips a bunch of the SDK resolution steps */
      val takDevVersion = libs.versions.takdev.get()
      classpath("com.github.jonapoul:atak-gradle-takdev:$takDevVersion")
    }
  }
}

plugins {
  alias(libs.plugins.doctor) // configured below

  id("convention-properties")
  id("convention-extras")
}

/* Won't work if we put this in settings.gradle.kts, because takdev plugin messes with it too much */
allprojects {
  repositories {
    mavenCentral()
    google()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("${rootProject.projectDir}/../maven") }
    mavenLocal()
  }
}

configurations.configureEach {
  resolutionStrategy {
    dependencySubstitution {
      substitute(module("net.sf.proguard:proguard-gradle"))
        .using(module(libs.plugin.proguard.get().toString()))
    }
  }
}

doctor {
  javaHome {
    ensureJavaHomeMatches.set(false)
    ensureJavaHomeIsSet.set(true)
    failOnError.set(true)
  }
}
