package vn.base.mvvm.screen.login.domainLayer

import kotlinx.coroutines.flow.FlowCollector
import vn.base.mvvm.core.delivery.Result
import vn.base.mvvm.core.base.usecase.BaseUseCase
import vn.base.mvvm.core.base.usecase.UseCaseParameters
import vn.base.mvvm.screen.login.dataLayer.repo.RepositoryAuth
import vn.base.mvvm.screen.login.state.StateLogin

class UseCaseLogin(
    private val repository: RepositoryAuth
) : BaseUseCase<StateLogin, UseCaseLogin.Params>() {

    override suspend fun FlowCollector<Result<StateLogin>>.run(params: Params) {
        emit(repository.login(params))
    }

    data class Params(
        val userName: String,
        val password: String,
        val times : String
    ) : UseCaseParameters
}
