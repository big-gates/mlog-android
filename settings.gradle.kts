pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "mlog"

include(":app")
include(":feature:home")
include(":core:design")
include(":core:domain")
include(":core:common")
include(":core:data")
include(":core:dataSourceLocal:room")
include(":core:dataSourceRemote:http")
include(":core:testing")
include(":core:model")
