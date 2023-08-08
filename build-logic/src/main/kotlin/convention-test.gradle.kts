import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.MetricType
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
    includes { packages("dev.jonpoulton.*") }
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
          includes { packages("dev.jonpoulton.kot") }
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
