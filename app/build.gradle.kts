plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ktlint)
    kotlin("plugin.serialization") version "1.9.0"
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin") // удалить?
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.wannahelp"
    compileSdk = 35

    sourceSets {
        getByName("main").java.srcDirs("build/generated/source/navigation-args")
    }

    defaultConfig {
        applicationId = "com.example.wannahelp"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.play.services.ads)
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":common"))
    implementation(project(":features:authCompose"))
//    implementation(project(":features:authorization"))
    implementation(project(":features:profile"))
    implementation(project(":features:categories"))
    implementation(project(":features:eventDetails"))
    implementation(project(":features:profileEditing"))
    implementation(project(":features:news"))
    implementation(project(":features:newsCompose"))
    implementation(project(":features:search"))

    ksp(libs.dagger.compiler)
    implementation(libs.dagger)

    implementation(libs.glide)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.wokrmanager)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.livedata.core)
    implementation(libs.kotlinx.datetime)
    api(libs.kotlinx.serialization.json)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
