import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.util.Properties
import kotlin.jvm.optionals.getOrNull

@Suppress("DEPRECATION")
fun Project.init() {
    VersionLoader.init(this)
    Property.init(this)
    KromBuildConfigLoader.init(this)
    gradle.buildFinished {
        VersionLoader.print(logger)
    }
}

fun Project.androidLibrary(action: Action<LibraryExtension>) = extensions.configure("android", action)

fun Project.androidApp(action: Action<AppExtension>) = extensions.configure("android", action)

val Project.isBuildVariantDebug: Boolean
    get() = gradle.startParameter.taskRequests.toString().contains("debug", true)

val Project.versionCatalogs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.findPluginFromVersionCatalog(alias: String) = versionCatalogs
    .findPlugin(alias)
    .getOrNull()
    ?.get()

fun Project.findVersionFromVersionCatalog(alias: String) = versionCatalogs
    .findVersion(alias)
    .getOrNull()

fun Project.findLibraryFromVersionCatalog(alias: String) = versionCatalogs
    .findLibrary(alias)
    .getOrNull()
    ?.get() as? Dependency

fun Project.configureObjectBox() = extensions.apply {
    val namespace = (findByName("android") as? BaseExtension)?.namespace ?: return@apply
    findByType<KaptExtension>()?.configureObjectBox(namespace)
}

fun Project.loadProperties(path: String): Properties {
    val file = file("${project.rootDir}/$path")
    val properties = Properties()
    if (file.canRead()) {
        properties.load(file.inputStream())
    }
    return properties
}

fun Project.log(message: String) = logger.lifecycle(message)