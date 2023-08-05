buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        val libs = project.extensions.getByName("libs") as org.gradle.accessors.dm.LibrariesForLibs

        classpath(libs.gradle.plugin.buildtools)
        classpath(libs.gradle.plugin.kotlin)
        classpath(libs.gradle.plugin.navigation.safeArgs)
    }
}

allprojects {
    repositories {
        google()
        maven(url = "https://maven.google.com")
        maven(url = "https://jitpack.io")
        mavenCentral()
    }
}

tasks {
    registering(Delete::class) {
        delete(buildDir)
    }
}

apply(from = "gradle/projectDependencyGraph.gradle")