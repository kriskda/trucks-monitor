apply(from = "../android.common.gradle")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("de.mobilej.unmock")
}

android {
    namespace = "io.github.trucksmonitor"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data:repository"))

    implementation(libs.coreKtx)
    implementation(libs.lifecycleRuntimeKtx)
    implementation(libs.activityCompose)
    implementation(libs.lifecycleRuntimeCompose)

    implementation(libs.coilCompose)

    implementation(libs.composeUi)
    implementation(libs.composeUiGraphics)
    debugImplementation(libs.composeUiToolingPreview)
    implementation(libs.composeMaterial3)

    implementation(libs.hiltAndroid)
    implementation(libs.hiltDaggerCore)
    implementation(libs.hiltAndroidXNavigationCompose)
    debugImplementation(libs.ui.tooling)
    ksp(libs.hiltDaggerCompiler)
    ksp(libs.hiltAndroidXCompiler)

    implementation(libs.composeDestinationAnimationsCore)
    implementation(libs.composeDestinationCore)
    ksp(libs.composeDestinationKsp)

    unmock(libs.android.all)

    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.testJunit)
    testImplementation(libs.testMockitoKotlin)
    testImplementation(libs.testCoroutines)
    testImplementation(libs.testCoroutines)
    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.testRobolectric)
}