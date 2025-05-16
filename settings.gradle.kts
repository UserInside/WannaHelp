pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WannaHelp"
include(":app")
include(":common")
include(":features")
include(":features:authorization")
include(":features:profile")
include(":features:categories")
include(":core")
include(":core:data")
include(":core:domain")
include(":features:profileEditing")
include(":features:news")
include(":features:search")
include(":features:eventDetails")
