apply(from = "../../android.common.gradle")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("de.mobilej.unmock")
}

android {
    namespace = "io.github.repository"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data:network"))

    implementation(libs.hiltDaggerCore)
    ksp(libs.hiltDaggerCompiler)

    unmock(libs.android.all)

    testImplementation(libs.testJunit)
    testImplementation(libs.testMockitoKotlin)
    testImplementation(libs.testCoroutines)
    testImplementation(libs.testRobolectric)
}