import java.net.URI

rootProject.name = "learnian"

pluginManagement {
    repositories {
        maven { url = uri("https://maven.myket.ir/") }

        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://maven.myket.ir/") }

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
include(":features:home")
include(":features:splash")
include(":shared")
