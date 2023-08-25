import org.gradle.api.Project
import java.io.File
import java.util.Properties

/**
 * Should only be declared during a TAK product center release pipeline run
 */
val Project.isPipeline: Boolean
  get() = System.getenv("ATAK_CI")?.toIntOrNull() == 1

val Project.takrepoUrl: String
  get() = properties["takrepo.url"]?.toString() ?: "https://localhost/"

val Project.takrepoUser: String
  get() = properties["takrepo.user"]?.toString() ?: "invalid"

val Project.takrepoPassword: String
  get() = properties["takrepo.password"]?.toString() ?: "invalid"

val Project.takDevPlugin: String
  get() = properties["takrepo.plugin"]?.toString()
    ?: "${rootProject.rootDir}/../ATAK-CIV-4.10.0.4-SDK/atak-gradle-takdev.jar"


/**
 * Fetch the value of the given [key] from either gradle.properties or local.properties
 */
fun Project.getProperty(key: String, defaultValue: String?): String? {
  val prop = project.properties[key] ?: getFromLocalProperties(key)
  return if (prop == null) defaultValue else prop as? String?
}

/**
 * Fetch the value of the given [key] from local.properties in the root project
 */
fun Project.getFromLocalProperties(key: String): String? {
  val localPropsFile = File(rootProject.projectDir, "local.properties")
  if (!localPropsFile.isFile || !localPropsFile.canRead()) {
    return null
  }
  val props = Properties()
  localPropsFile.reader().use { props.load(it) }
  return props[key] as? String?
}
