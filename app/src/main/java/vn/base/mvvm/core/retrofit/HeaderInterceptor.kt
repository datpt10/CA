package vn.base.mvvm.core.retrofit

import okhttp3.Interceptor
import vn.base.mvvm.data.local.LocalStorage

class HeaderInterceptor(
    private val versionName: String,
    private val localStorage: LocalStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(
        chain.appRequestBuilder(
            versionName = versionName,
            token = null,
            deviceId = localStorage.getDeviceId()
        )
    )
}