package base.plugin.configure

import AndroidConfig
import base.plugin.internal.applicationExtension
import base.plugin.internal.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import java.io.FileInputStream
import java.util.*

@Suppress("UnstableApiUsage")
internal fun Project.configureAndroidApplication() = applicationExtension.run {

    defaultConfig {
        applicationId = AndroidConfig.APPLICATION_ID
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        compileSdk = libs.versions.compileSdk.get().toInt()
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

        setProperty("archivesBaseName", AndroidConfig.archivesBaseName)
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildFeatures {
        viewBinding = AndroidConfig.VIEW_BINDING_ENABLED
//        dataBinding = AndroidConfig.DATA_BINDING_ENABLED
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            enableAndroidTestCoverage = false
            isShrinkResources = true
            extra["enableCrashlytics"] = true
            proguardFiles(
                "proguard-rules.pro",
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
        }

        debug {
            isDebuggable = true
            isMinifyEnabled = false
            enableAndroidTestCoverage = true
            extra["enableCrashlytics"] = false

//            applicationIdSuffix = ".debug"
//            versionNameSuffix = "-debug"
        }
    }

    signingConfigs {

        getByName("debug") {
//            val keystorePropertiesFile = file("../keystore/debug.properties")
//            if (!keystorePropertiesFile.exists()) {
//                return@getByName
//            }
//
//            val keystoreProperties = Properties()
//            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
//
//            keyAlias = keystoreProperties["keyAlias"] as String
//            keyPassword = keystoreProperties["keyPassword"] as String
//            storeFile = file(keystoreProperties["storeFile"] as String)
//            storePassword = keystoreProperties["storePassword"] as String
        }

        register("release") {
//            val keystorePropertiesFile = file("../keystore/release.properties")
//            if (!keystorePropertiesFile.exists()) {
//                logger.warn("Release builds may not work: signing config not found.")
//                return@register
//            }
//
//            val keystoreProperties = Properties()
//            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
//
//            keyAlias = keystoreProperties["keyAlias"] as String
//            keyPassword = keystoreProperties["keyPassword"] as String
//            storeFile = file(keystoreProperties["storeFile"] as String)
//            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
