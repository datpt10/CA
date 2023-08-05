package vn.base.mvvm.screen.splash.dataLayer.service

import retrofit2.awaitResponse
import vn.base.mvvm.screen.splash.dataLayer.ApiSplash
import vn.base.mvvm.core.retrofit.SafeExecute

class ServiceSplash(val apiSplash: ApiSplash) : SafeExecute() {

    suspend fun checkVersion(currentVersion: String) =
        execute {
            apiSplash.checkVersion(
                version = currentVersion
            ).awaitResponse()
        }
}