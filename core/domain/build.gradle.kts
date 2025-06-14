import de.mannodermaus.gradle.plugins.junit5.junitPlatform

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")

}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {

    // Dagger
    ksp(libs.dagger.compiler)
    implementation(libs.dagger)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.converter.kotlinx.serialization)

    implementation(libs.retrofit)

    testImplementation(libs.junit.jupiter)

}