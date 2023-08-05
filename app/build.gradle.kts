plugins {
    id(Plugins.BASE)
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_KAPT)
}

android {
    flavorDimensions.add(FlavorDimensions.ENVIRONMENT)
    productFlavors {
        createApplicationFlavor(
                production = {
                    buildConfigField("String", "BASE_URL", "\"http://linkpro.vn\"")
                    resValue("string", "app_name", "CA")
                },
                dev = {
                    buildConfigField("String", "BASE_URL", "\"http://linkdev.vn\"")
                    resValue("string", "app_name", "CA-DEV")
                }
        )
    }

    buildFeatures {
        viewBinding = AndroidConfig.VIEW_BINDING_ENABLED
//        dataBinding = AndroidConfig.DATA_BINDING_ENABLED
    }

    lint {
        abortOnError = false
        disable.add("Instantiatable")
    }
}

dependencies {
    implementation(projects.ui)
    implementation(projects.localization)

    implementation(libs.koin.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.android.material)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.easyPermission)
    implementation(libs.customview.alertTop)
    implementation(libs.networkMonitor)
    implementation(libs.timber)
    implementation(libs.gson)
    implementation(libs.biometric)

    //Room
    implementation(libs.roomKtx)
    implementation(libs.roomRuntime)
    kapt(libs.roomCompiler)

    implementation(libs.androidx.paging)

    implementation(libs.glide)
    kapt(libs.glideCompiler)
    implementation(libs.glideWebpDecoder)

    implementation(libs.androidx.navigation.ui)

    implementation(libs.retrofit)
    implementation(libs.retrofitGsonConverter)

    implementation(platform(libs.okHttpBom))
    implementation(libs.okHttpLogging)
    implementation(libs.okHttp)


    testImplementation(libs.testing.junit)
    androidTestImplementation(libs.testing.androidx.junit)
    androidTestImplementation(libs.testing.espresso.core)
}