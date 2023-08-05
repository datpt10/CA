package vn.base.mvvm.screen.splash.dataLayer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vn.base.mvvm.data.network.ENDPOINT
import vn.base.mvvm.screen.splash.data.response.VersionResponse

interface ApiSplash {

    @GET(ENDPOINT.APP_VERSION)
    fun checkVersion(
        @Query("platform") platform: String = "android",
        @Query("app_type") app_type: String = "mod",
        @Query("version") version: String
    ): Call<VersionResponse>
}