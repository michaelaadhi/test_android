@file:Suppress("UnstableApiUsage")
plugins {
    applyAndroidApplication()
    applyKotlinAndroid()
    applyKotlinKapt()
    applyKotlinParcelize()
    applyGoogleKsp()
    applyObjectBox()
}

android {
    namespace = "com.mike.project"
    compileSdk = libs.versions.build.sdk.compile.get().toInt()
//    ndkVersion = libs.versions.build.ndk.get()

    defaultConfig {
        minSdk = libs.versions.build.sdk.min.get().toInt()
        targetSdk = libs.versions.build.sdk.target.get().toInt()
        versionCode = VersionLoader.versionCode
        versionName = VersionLoader.versionName
    }

    bundle {
        // prevent language/string resources splitting during installation
        language {
            enableSplit = false
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
            applicationIdSuffix = ".staging"
            dimension = "server"
        }
    }

    androidComponents {
        beforeVariants { variantBuilder ->
            variantBuilder.enable = variantBuilder.name.contains("staging") || variantBuilder.name.contains("production")
        }
    }

    lint {
        disable.add("NotifyDataSetChanged")
        disable.add("ContentDescription")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    coreLibraryDesugaring(libs.android.tools.desugar)

    implementation(libs.bundles.kotlin.base)

    implementation(libs.androidx.core)
    implementation(libs.androidx.app.compat)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.swipe.refresh.layout)
    implementation(libs.android.installreferrer)
    implementation(libs.bundles.androidx.lifecycle)

    implementation(libs.google.gson)
    implementation(libs.google.flexbox)
    implementation(libs.google.material)
    implementation(libs.google.library.places)
    implementation(libs.google.play.core)
    implementation(libs.google.play.auth.main)
    implementation(libs.google.play.auth.phone)
    implementation(libs.google.play.analytics)
    implementation(libs.google.play.maps)
    implementation(libs.google.play.appset)
    implementation(libs.google.play.mlkit.barcode)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.appcheck.debug)
    implementation(libs.firebase.config)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.perf)
    implementation(libs.firebase.play.integrity)

    implementation(libs.misc.calendar.view)
    implementation(libs.misc.fotoapparat)
    implementation(libs.misc.jsoup)
    implementation(libs.misc.lottie)
    implementation(libs.misc.relinker)
    implementation(libs.misc.rootbeer)
    implementation(libs.misc.shimmer)
    implementation(libs.misc.glide.main)
    ksp(libs.misc.glide.compiler)
    implementation(libs.misc.glide.okhttp.integration) {
        exclude(group = "glide-parent")
    }

    implementation(platform(libs.squareup.okhttp.bom))
    implementation(libs.squareup.okhttp.main)
    implementation(libs.squareup.okhttp.logging)
    implementation(libs.squareup.retrofit.main)
    implementation(libs.squareup.retrofit.gson)
    implementation(libs.squareup.otto)
}