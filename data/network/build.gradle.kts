apply(from = "../../android.common.gradle")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "io.github.network"
}

dependencies {
    implementation(libs.hiltDaggerCore)
    ksp(libs.hiltDaggerCompiler)

    implementation(libs.retrofit)
    implementation(libs.retrofitGson)

    testImplementation(libs.testJunit)
    testImplementation(libs.testMockitoKotlin)
    testImplementation(libs.testCoroutines)
}