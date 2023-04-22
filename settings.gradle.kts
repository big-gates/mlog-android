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
        maven { setUrl("https://jitpack.io") }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "mlog"

include(":app")
include(":feature:home")
include(":feature:mypage")
include(":core:design")
include(":core:domain")
include(":core:common")
include(":core:data")
include(":core:dataSourceLocal:room")
include(":core:dataSourceRemote:http")
include(":core:testing")
include(":core:model")
include(":feature:search")
include(":feature:movie_modal")
