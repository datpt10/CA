package vn.base.mvvm.screen.splash.domainLayer

import kotlinx.coroutines.flow.FlowCollector
import vn.base.mvvm.core.delivery.Result
import vn.base.mvvm.core.base.usecase.BaseUseCase
import vn.base.mvvm.core.base.usecase.None
import vn.base.mvvm.screen.splash.data.local.LatestVersion
import vn.base.mvvm.screen.splash.dataLayer.repo.RepositorySplash

class UseCaseCheckVersion(
    private val repository: RepositorySplash
) : BaseUseCase<VersionState, None>() {

    override suspend fun FlowCollector<Result<VersionState>>.run(params: None) {
        emit(repository.checkVersion())
    }
}

sealed class VersionState {
    object LatestRelease : VersionState()

    //FLEXIBLE
    data class OptionalUpdate(val data: LatestVersion) : VersionState()

    //IMMEDIATE
    data class ForceUpdate(val data: LatestVersion) : VersionState()
}