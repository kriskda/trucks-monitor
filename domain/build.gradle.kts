apply(from = "../android.common.gradle")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("de.mobilej.unmock")
}

android {
    namespace = "io.github.domain"
}

dependencies {
    implementation(libs.hiltDaggerCore)

    unmock(libs.android.all)

    testImplementation(libs.testJunit)
    testImplementation(libs.testMockitoKotlin)
    testImplementation(libs.testCoroutines)
}