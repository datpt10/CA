package vn.base.mvvm.screen.splash.domainLayer

import kotlinx.coroutines.flow.FlowCollector
import vn.base.mvvm.core.base.usecase.BaseUseCase
import vn.base.mvvm.core.base.usecase.None
import vn.base.mvvm.core.delivery.Result
import vn.base.mvvm.screen.splash.dataLayer.repo.RepositorySplash

class UseCaseSplash(
    private val repository: RepositorySplash
) : BaseUseCase<SplashState, None>() {

    override suspend fun FlowCollector<Result<SplashState>>.run(params: None) {
        emit(repository.initial())
    }
}

sealed class SplashState {
    object OpenLogin : SplashState()

    object OpenDashBoard : SplashState()
}