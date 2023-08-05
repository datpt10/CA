package vn.base.mvvm.core.retrofit

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Request
import java.util.Locale

fun Interceptor.Chain.appRequestBuilder(
    versionName: String,
    token: String? = null,
    deviceId: String? = null
) = run {
    val original = request()
    val originalRequest: Request
    synchronized(this) {
        val requestBuilder = original.newBuilder().apply {
            addHeader("Accept", "application/json")
            addHeader("Content-type", "application/json")
//            addHeader("Accept-Language", getLanguageDefault())
            val deviceVersion = android.os.Build.VERSION.RELEASE
            val deviceModel = android.os.Build.MODEL
            val deviceManufacturer = android.os.Build.MANUFACTURER
            addHeader(
                "user-agent",
                "CA/$versionName (Android $deviceVersion; $deviceModel; $deviceManufacturer)"
            )
            if (token?.isNotBlank() == true) {
                addHeader("Authorization", "Bearer $token")
            }

            deviceId?.let {
                if (!TextUtils.isEmpty(deviceId)) {
                    addHeader("device_id", deviceId)
                }
            }
            method(original.method, original.body)
        }
        originalRequest = requestBuilder.build()
    }
    originalRequest
}

/**
 * get default language header
 * Support: Vietnamese, English, Thai
 * return: language
 */
fun getLanguageDefault() = when (val language = Locale.getDefault().language) {
    "vi" -> language
    else -> "en"
}