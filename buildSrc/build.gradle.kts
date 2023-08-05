@file:Suppress("UnstableApiUsage")

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("BasePlugin") {
            id = "BasePlugin"
            implementationClass = "base.plugin.BasePlugin"
        }
    }
}


dependencies {
    /* Depend on the android gradle plugin, since we want to access it in our plugin */
    implementation(libs.gradle.plugin.buildtools)

    /* Example Dependency */
    /* Depend on the kotlin plugin, since we want to access it in our plugin */
    implementation(libs.gradle.plugin.kotlin)

    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
    implementation(localGroovy())

    /*Make version catalogs accessible from precompiled script plugins*/
    /*https://github.com/gradle/gradle/issues/15383#issuecomment-779893192*/
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}