plugins {
    id(Plugins.BASE)
    id(Plugins.ANDROID_LIBRARY)
}

dependencies {
    implementation(libs.kotlin)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.appcompat)
    implementation("androidx.exifinterface:exifinterface:1.3.3")
}