plugins {
  `kotlin-dsl`
}

dependencies {
  implementation(libs.plugin.agp)
  implementation(libs.plugin.detekt)
  implementation(libs.plugin.kotlin)
  implementation(libs.plugin.kover)
  implementation(libs.plugin.ktlint)
  implementation(libs.plugin.licensee)
  implementation(libs.plugin.spotless)

  // https://stackoverflow.com/a/70878181/15634757
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
  implementation(files(projects.javaClass.superclass.protectionDomain.codeSource.location))
}
