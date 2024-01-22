// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.tools.gradle)
        classpath(libs.jetbrains.kotlin.gradle)
        classpath(libs.objectbox.plugin)
        classpath(libs.bugsnag.plugin)
    }
}

plugins {
    alias(libs.plugins.google.devtools.ksp) apply false
}

init()

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}