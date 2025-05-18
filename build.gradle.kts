// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.devtools.ksp") version "2.1.10-1.0.30" apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    id("androidx.navigation.safeargs") version "2.8.9" apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        val safeArgs = "2.8.9"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$safeArgs")
    }
}