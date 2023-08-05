package vn.base.mvvm.screen.login.dataLayer.repo

import timber.log.Timber
import vn.base.mvvm.utils.extensions.logE
import vn.base.mvvm.core.delivery.ActionDone
import vn.base.mvvm.core.delivery.Result
import vn.base.mvvm.core.delivery.toResultData
import vn.base.mvvm.data.local.LocalStorage
import vn.base.mvvm.screen.login.dataLayer.service.ServiceAuth
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogin
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogoutRefreshToken
import vn.base.mvvm.screen.login.domainLayer.mapper.UserMapper
import vn.base.mvvm.screen.login.state.StateLogin

class RepositoryAuthImpl(
    private val localStorage: LocalStorage,
    private val api: ServiceAuth,
    private val userMapper: UserMapper,
) : RepositoryAuth {

    override suspend fun login(params: UseCaseLogin.Params) =
        when (val result = api.login(params = params)) {
            is Result.Success -> result.successData.content?.let {
//            saveData(it)
//            val user = userMapper.convert(result.successData.content)
                when {
                    it.accessToken.isNotEmpty() -> {
                        Result.Success(StateLogin.OpenDashboard)
                    }

                    else -> Result.Success(StateLogin.OpenPermission)
                }
            } ?: run {
                Result.Success(StateLogin.OpenChangePass)
            }

            is Result.Failure -> Result.Failure(result.reason)
            else -> Result.Loading
        }

    override suspend fun logoutRefreshToken(body: UseCaseLogoutRefreshToken.Params): Result<ActionDone> {
        return api.logoutRefreshToken(body).toResultData()
    }
}