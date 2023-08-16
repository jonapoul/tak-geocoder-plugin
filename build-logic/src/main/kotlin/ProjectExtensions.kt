import org.gradle.api.Project
import java.io.File
import java.util.Properties

/**
 * Should only be declared during a TAK product center release pipeline run
 */
val Project.isPipeline: Boolean
  get() = properties["takrepo.url"] != null

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
