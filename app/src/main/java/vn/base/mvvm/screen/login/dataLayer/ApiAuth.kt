package vn.base.mvvm.screen.login.dataLayer

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import vn.base.mvvm.core.delivery.EmptyResponse
import vn.base.mvvm.data.network.ENDPOINT
import vn.base.mvvm.screen.login.data.response.LoginResponse
import vn.base.mvvm.screen.login.data.response.TokenResponse
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogin
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogoutRefreshToken

interface ApiAuth {

    @POST(ENDPOINT.AUTH_LOGIN)
    fun login(
        @Body body: UseCaseLogin.Params,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST(ENDPOINT.AUTH_REFRESH_TOKEN)
    fun refreshToken(
        @Field("refresh_token") refreshToken: String,
        @Field("device_id") device_id: String
    ): Call<TokenResponse>

    @POST(ENDPOINT.AUTH_LOGOUT)
    fun logout(
        @Header("Authorization") token: String
    ): Call<EmptyResponse>

    @POST(ENDPOINT.AUTH_LOGOUT_REFRESH_TOKEN)
    fun logoutRefreshToken(@Body body: UseCaseLogoutRefreshToken.Params): Call<EmptyResponse>
}