package vn.base.mvvm.screen.login.dataLayer.service

import retrofit2.awaitResponse
import vn.base.mvvm.screen.login.dataLayer.ApiAuth
import vn.base.mvvm.core.retrofit.SafeExecute
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogin
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogoutRefreshToken

class ServiceAuth(private val apiAuth: ApiAuth) : SafeExecute() {

    suspend fun login(params: UseCaseLogin.Params) = execute {
        apiAuth.login(params).awaitResponse()
    }

    suspend fun logout(token: String) = execute {
        apiAuth.logout("Bearer $token").awaitResponse()
    }

    suspend fun logoutRefreshToken(body: UseCaseLogoutRefreshToken.Params) = execute {
        apiAuth.logoutRefreshToken(body).awaitResponse()
    }
}