buildscript {
  val isPipeline = System.getenv("ATAK_CI")?.toIntOrNull() == 1
  val takrepoUrl = project.properties["takrepo.url"] ?: "https://localhost/"
  val takrepoUser = project.properties["takrepo.user"]?.toString() ?: "invalid"
  val takrepoPassword = project.properties["takrepo.password"]?.toString() ?: "invalid"
  val takDevPlugin = project.properties["takrepo.plugin"]
    ?: "${rootProject.rootDir}/../ATAK-CIV-4.10.0.4-SDK/atak-gradle-takdev.jar"

  extra.apply {
    set("takrepoUrl", takrepoUrl)
    set("takrepoUser", project.properties["takrepo.user"] ?: "invalid")
    set("takrepoPassword", project.properties["takrepo.password"] ?: "invalid")
    set("takdevPlugin", takDevPlugin)
    set("takdevConTestEnable", false)
    set("takdev.verbose", true)
    set("ATAK_VERSION", libs.versions.atak.get())
    set("PLUGIN_VERSION", libs.versions.pluginVersionName.get())

    if (!isPipeline) {
      /* Only needed to work around weirdness with the takdev plugin with my debug fork */
      set("takrepo.devkit.version", "4.10.2")
    }
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
      maven {
        url = uri(takrepoUrl)
        credentials {
          username = takrepoUser
          password = takrepoPassword
        }
      }
    } else {
      mavenLocal()
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
      println("Loading proper takdev")
      classpath("com.atakmap.gradle:atak-gradle-takdev:2.+")
    } else {
      println("Loading debug takdev from local maven")
      classpath("com.atakmap.gradle:atak-gradle-takdev:2.4.1-debug3")
    }
  }
}

plugins {
  alias(libs.plugins.doctor) // configured below
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
