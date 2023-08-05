package vn.base.mvvm.core.retrofit

import android.text.TextUtils
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import vn.base.mvvm.core.bus.EventBus
import vn.base.mvvm.data.local.LocalStorage
import vn.base.mvvm.screen.login.dataLayer.ApiAuth
import vn.base.mvvm.data.network.event.RefreshTokenFailed
import java.net.HttpURLConnection

class HeaderAuthenticationInterceptor(
    private val versionName: String,
    private val localStorage: LocalStorage,
    private val apiAuth: ApiAuth
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.appRequestBuilder(
            versionName = versionName,
            token = null,
            deviceId = localStorage.getDeviceId()
        )
        val initialResponse = chain.proceed(originalRequest)
        synchronized(this) {

            when (initialResponse.code) {
                HttpURLConnection.HTTP_FORBIDDEN, HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    try {
                        "localStorage.getAuthRefreshToken()".let {

                            //fix bug cannot make a new request because the previous response is still open: please call response.close()
                            initialResponse.close()

                            //RUN BLOCKING!!
                            val responseRefresh = runBlocking {
                                apiAuth.refreshToken(it, localStorage.getDeviceId()).execute()
                            }

                            return when {
                                responseRefresh.code() != 0 -> {
                                    val failed =
                                        responseRefresh.errorBody()?.let { responseErrorBody ->
                                            val errorBody = responseErrorBody.string()
                                            Gson().fromJson(
                                                errorBody,
                                                RefreshTokenFailed::class.java
                                            )
                                        } ?: RefreshTokenFailed()

                                    EventBus.post(failed)
                                    initialResponse
                                }

                                else -> {
                                    responseRefresh.body()?.content?.let { tokenData ->
                                        tokenData.apply {
                                            if (!TextUtils.isEmpty("accessToken")
                                                && !TextUtils.isEmpty("refresh_token")
                                            )
                                                localStorage.saveAuthToken(
                                                    accessToken,
                                                    "refresh_token"
                                                )
                                        }
                                    }
                                    val newAuthenticationRequest =
                                        originalRequest.newBuilder()
                                            .removeHeader("Authorization")
                                            .addHeader(
                                                "Authorization",
                                                "Bearer ${responseRefresh.body()?.content?.accessToken}"
                                            ).build()
                                    chain.proceed(newAuthenticationRequest)
                                }
                            }
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        return initialResponse
                    }
                }

                else -> return initialResponse
            }
        }
        return initialResponse
    }
}