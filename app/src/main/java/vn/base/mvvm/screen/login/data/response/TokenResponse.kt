package vn.base.mvvm.screen.login.data.response

import vn.base.mvvm.core.delivery.BaseResponse

data class TokenData(
    val accessToken: String,
//    val expired_at: Int,
//    val refresh_token: String
)

class TokenResponse : BaseResponse<TokenData>()