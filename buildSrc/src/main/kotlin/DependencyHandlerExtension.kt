import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.api(dependency: Any?) = dependency?.let { add("api", it) }

fun DependencyHandler.implementation(dependency: Any?) = dependency?.let { add("implementation", it) }

fun DependencyHandler.coreLibraryDesugaring(dependency: Any?) = dependency?.let { add("coreLibraryDesugaring", it) }

fun DependencyHandler.kapt(dependency: Any?) = dependency?.let { add("kap", it) }

fun DependencyHandler.ksp(dependency: Any?) = dependency?.let { add("ksp", it) }

fun DependencyHandler.debugImplementation(dependency: Any?) = dependency?.let { add("debugImplementation", it) }

fun DependencyHandler.releaseImplementation(dependency: Any?) = dependency?.let { add("releaseImplementation", it) }