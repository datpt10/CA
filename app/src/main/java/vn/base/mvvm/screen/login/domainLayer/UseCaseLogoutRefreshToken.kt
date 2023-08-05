package vn.base.mvvm.screen.login.domainLayer

import kotlinx.coroutines.flow.FlowCollector
import vn.base.mvvm.core.delivery.ActionDone
import vn.base.mvvm.core.delivery.Result
import vn.base.mvvm.core.base.usecase.BaseUseCase
import vn.base.mvvm.core.base.usecase.UseCaseParameters
import vn.base.mvvm.screen.login.dataLayer.repo.RepositoryAuth

class UseCaseLogoutRefreshToken(
    private val repository: RepositoryAuth
) : BaseUseCase<ActionDone, UseCaseLogoutRefreshToken.Params>() {
    override suspend fun FlowCollector<Result<ActionDone>>.run(params: Params) {
        emit(repository.logoutRefreshToken(params))
    }

    data class Params(
        val device_id: String,
        val refresh_token: String
    ) : UseCaseParameters
}