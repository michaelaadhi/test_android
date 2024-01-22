package com.krom.gradle.plugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import androidLibrary
import findLibraryFromVersionCatalog
import findVersionFromVersionCatalog
import coreLibraryDesugaring
import asString

class KromLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configurePlugin()
            configureAndroid()
            configureDependency()
        }
    }

    private fun Project.configurePlugin() = plugins.apply {
        apply("com.android.library")
        apply("kotlin-android")
        apply("kotlin-kapt")
        apply("kotlin-parcelize")
    }

    private fun Project.configureAndroid() = androidLibrary {
        compileSdk = findVersionFromVersionCatalog("build-sdk-compile")?.displayName?.toInt()!!

        defaultConfig {
            minSdk = findVersionFromVersionCatalog("build-sdk-min")?.displayName?.toInt()
            //targetSdk = compileSdk
        }

        buildTypes {
            release {
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }

        buildFeatures {
            viewBinding = true
            dataBinding = true
        }

        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        flavorDimensions += "server"
        productFlavors {
            create("production") {
                dimension = "server"
            }
            create("staging") {
                dimension = "server"
            }
        }

        lint {
            disable.add("NotifyDataSetChanged")
            disable.add("ContentDescription")
        }
    }

    private fun Project.configureDependency() = dependencies {
        coreLibraryDesugaring(findLibraryFromVersionCatalog("android-tools-desugar")?.asString())
    }
}