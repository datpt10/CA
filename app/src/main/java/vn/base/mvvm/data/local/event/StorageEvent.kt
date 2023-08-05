package vn.base.mvvm.data.local.event

import vn.base.mvvm.screen.profile.data.local.UserInfo

sealed class StorageEvent {
    object IDLE : StorageEvent()

    data class TokenChanged(val token: String? = null, val refreshToken: String? = null) :
        StorageEvent()

    data class ProfileChanged(val profile: UserInfo? = null) : StorageEvent()
}