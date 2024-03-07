buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.hiltGradlePlugin)
        classpath(libs.unmockplugin)
    }
}
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.20" apply false
    id("com.android.library") version "8.2.2" apply false
}