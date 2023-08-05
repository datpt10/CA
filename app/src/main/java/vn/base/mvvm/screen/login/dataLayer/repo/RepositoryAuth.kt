package vn.base.mvvm.screen.login.dataLayer.repo

import vn.base.mvvm.core.delivery.ActionDone
import vn.base.mvvm.core.delivery.Result
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogin
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogoutRefreshToken
import vn.base.mvvm.screen.login.state.StateLogin

interface RepositoryAuth {
    suspend fun login(params: UseCaseLogin.Params): Result<StateLogin>

    //    suspend fun logout(): Result<ActionDone>
//
    suspend fun logoutRefreshToken(body: UseCaseLogoutRefreshToken.Params): Result<ActionDone>
}