package vn.base.mvvm.core.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val REQUEST_TIMEOUT = 25L

fun okHttpClient(
    headerInterceptor: Interceptor,
    loggingInterceptor: HttpLoggingInterceptor
) = OkHttpClient.Builder()
    .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
    .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
    .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
    .addInterceptor(headerInterceptor)
    .addInterceptor(loggingInterceptor)
    .build()

fun okHttpClientAuthentication(
    headerInterceptor: Interceptor,
    loggingInterceptor: HttpLoggingInterceptor
) = OkHttpClient.Builder()
    .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
    .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
    .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
    .addInterceptor(headerInterceptor)
    .addInterceptor(loggingInterceptor)
    .build()

fun loggingInterceptor(isDebug: Boolean = false) =
    HttpLoggingInterceptor().setLevel(
        if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    )

fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()