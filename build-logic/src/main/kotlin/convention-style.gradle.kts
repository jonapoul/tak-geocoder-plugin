import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.accessors.dm.LibrariesForLibs
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
  id("app.cash.licensee")
  id("com.diffplug.spotless")
  id("io.gitlab.arturbosch.detekt")
  id("org.jlleitschuh.gradle.ktlint")
}

val libs = the<LibrariesForLibs>()

licensee {
  listOf(
    "Apache-2.0", // Most libraries
    "MIT", // Timber-JUnit
    "EPL-1.0", // JUnit
  ).forEach { license ->
    allow(license)
  }

  listOf(
    "http://www.opensource.org/licenses/bsd-license.php", // Hamcrest
  ).forEach { url ->
    allowUrl(url)
  }
}

detekt {
  config.setFrom(files("${rootProject.rootDir}/detekt.yml"))
  buildUponDefaultConfig = true
}

tasks.withType<Detekt> {
  reports.html.required.set(true)
}

if (tasks.any { it.name == "detektMain" }) {
  val detektMain by tasks
  val check by tasks
  check.dependsOn(detektMain)
}

ktlint {
  version.set(libs.versions.ktlint.cli.get())
  reporters {
    reporter(ReporterType.HTML)
  }
}

spotless {
  format("misc") {
    target("*.gradle", "*.gitignore", "*.pro")
    indentWithSpaces()
    trimTrailingWhitespace()
    endWithNewline()
  }
  json {
    target("*.json")
    simple()
  }
  yaml {
    target("*.yml", "*.yaml")
    jackson()
  }
}
