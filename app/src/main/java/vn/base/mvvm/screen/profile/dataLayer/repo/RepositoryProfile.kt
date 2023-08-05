package vn.base.mvvm.screen.profile.dataLayer.repo

import vn.base.mvvm.screen.profile.data.local.UserInfo
import vn.base.mvvm.core.delivery.Result

interface RepositoryProfile {

    suspend fun myInformation(): Result<UserInfo>

}