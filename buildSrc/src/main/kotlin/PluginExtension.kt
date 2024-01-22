import org.gradle.plugin.use.PluginDependenciesSpec

fun PluginDependenciesSpec.applyKromLibrary() = id("krom-library-plugin")

fun PluginDependenciesSpec.applyAndroidApplication() = id("com.android.application")

fun PluginDependenciesSpec.applyAndroidLibrary() = id("com.android.library")

fun PluginDependenciesSpec.applyKotlinAndroid() = id("org.jetbrains.kotlin.android")

fun PluginDependenciesSpec.applyKotlinKapt() = id("org.jetbrains.kotlin.kapt")

fun PluginDependenciesSpec.applyKotlinParcelize() = id("org.jetbrains.kotlin.plugin.parcelize")

fun PluginDependenciesSpec.applyGoogleService() = id("com.google.gms.google-services")

fun PluginDependenciesSpec.applyGoogleKsp() = id("com.google.devtools.ksp")

fun PluginDependenciesSpec.applyFirebaseCrashlytics() = id("com.google.firebase.crashlytics")

fun PluginDependenciesSpec.applyFirebasePerf() = id("com.google.firebase.firebase-perf")

fun PluginDependenciesSpec.applyFirebaseAppDistribution() = id("com.google.firebase.appdistribution")

fun PluginDependenciesSpec.applyObjectBox() = id("io.objectbox")