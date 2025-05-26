import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            "String", "BASE_URL", project.properties["BASE_URL"] as String
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":common"))

    api(libs.kotlinx.serialization.json)
    implementation(libs.converter.kotlinx.serialization)

    // OkHttp & Retrofit
    implementation(libs.retrofit)
    implementation(platform(libs.okhttp.bom))
    api(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Dagger
    ksp(libs.dagger.compiler)
    implementation(libs.dagger)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Android
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}