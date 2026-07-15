pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Comics"

include(":app")

include(":core:common")
include(":core:mvi")
include(":core:network")
include(":core:ui")

include(":domain")
include(":data")

include(":feature:clans")
