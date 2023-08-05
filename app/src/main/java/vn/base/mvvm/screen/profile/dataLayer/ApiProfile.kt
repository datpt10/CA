package vn.base.mvvm.screen.profile.dataLayer

import retrofit2.Call
import retrofit2.http.GET
import vn.base.mvvm.data.network.ENDPOINT
import vn.base.mvvm.screen.profile.data.response.UserResponse

interface ApiProfile {
    @GET(ENDPOINT.ME)
    fun myInformation(): Call<UserResponse>
}