import java.net.URI

rootProject.name = "learnian"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = URI.create("https://jitpack.io") }
    }
}

include(":androidApp")
include(":core-database")
include(":core-datastore")
include(":core-network")
include(":core-system")
include(":core-view")
include(":shared")
