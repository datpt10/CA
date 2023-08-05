enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        google()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
        mavenCentral()
    }
}

rootProject.name = "CA"

//libraries module
include(":library:choosePhotoHelper")
include(":library:spannedgridlayoutmanager")
include(":localization")

//app
include(":app")
include(":ui")