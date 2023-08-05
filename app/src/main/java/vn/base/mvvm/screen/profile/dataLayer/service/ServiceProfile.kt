package vn.base.mvvm.screen.profile.dataLayer.service

import retrofit2.awaitResponse
import vn.base.mvvm.screen.profile.dataLayer.ApiProfile
import vn.base.mvvm.core.retrofit.SafeExecute

class ServiceProfile(private val api: ApiProfile) : SafeExecute() {

    suspend fun myInformation() = execute {
        api.myInformation().awaitResponse()
    }
}