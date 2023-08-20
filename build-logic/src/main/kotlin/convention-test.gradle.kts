@file:Suppress("UnstableApiUsage")

import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.MetricType
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
  id("org.jetbrains.kotlinx.kover")
}

val excludeClasses = listOf(
  /* Generated files */
  "*.BuildConfig",
  "*_Impl*",

  /* ViewBinding */
  "*.databinding.*",

  /* DI Modules */
  "*.di.*",

  /* UI Classes */
  "*.ui.*Adapter*",
  "*.ui.*Animator*",
  "*.ui.*Button*",
  "*.ui.*Dialog*",
  "*.ui.*Fragment*",
  "*.ui.*Layout*",
  "*.ui.*View*",
  "*.ui.*ViewHolder*",

  /* Serialization */
  "*\$\$serializer*",
)

koverReport {
  filters {
    includes { packages("dev.jonpoulton") }
    excludes { classes(excludeClasses) }
  }

  defaults {
    html {
      onCheck = true
    }

    verify {
      onCheck = true
      rule {
        isEnabled = true
        filters {
          includes { packages("dev.jonpoulton") }
        }
        bound {
          minValue = 60
          metric = MetricType.INSTRUCTION
          aggregation = AggregationType.COVERED_PERCENTAGE
        }
      }
    }
  }
}

val libs = the<LibrariesForLibs>()
val testImplementation by configurations

dependencies {
  testImplementation(libs.kotlin.coroutines.core)
  testImplementation(libs.test.alakazam.core)
  testImplementation(libs.test.junit)
  testImplementation(libs.test.kotlin.common)
  testImplementation(libs.test.kotlin.coroutines)
  testImplementation(libs.test.kotlin.junit)
  testImplementation(libs.test.mockk)
  testImplementation(libs.test.okhttp.webserver)
  testImplementation(libs.test.timber)
  testImplementation(libs.test.turbine)
}

if (project.name != "app") {
  val app = project(":app")
  app.dependencies {
    kover(project)
  }
}

tasks.withType<Test> {
  testLogging {
    exceptionFormat = TestExceptionFormat.FULL
    showCauses = true
    showExceptions = true
    showStackTraces = true
    showStandardStreams = true
  }
}
