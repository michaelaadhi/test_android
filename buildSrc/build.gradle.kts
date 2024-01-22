plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

gradlePlugin {
    plugins {
        register("krom-library-plugin") {
            id = "krom-library-plugin"
            implementationClass = "com.krom.gradle.plugins.KromLibraryPlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.android.tools.gradle)
    implementation(libs.android.tools.gradle.api)
    implementation(libs.jetbrains.kotlin.gradle)
    implementation(libs.google.gson)
    implementation(libs.gradle.bouncycastle)
    implementation(libs.gradle.json)
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
    kotlinOptions {
        jvmTarget = JavaVersion.current().toString()
    }
}