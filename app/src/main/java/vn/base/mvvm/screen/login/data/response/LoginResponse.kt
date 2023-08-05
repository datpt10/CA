package vn.base.mvvm.screen.login.data.response

import com.google.gson.annotations.SerializedName
import vn.base.mvvm.core.delivery.BaseResponse
import vn.base.mvvm.screen.profile.data.local.UserInfo
import java.io.Serializable

class LoginResponse : BaseResponse<ContentEntity>()

data class ContentEntity(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("userInfo") val userInfo: UserInfo,
) : Serializable