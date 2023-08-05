package vn.base.mvvm.screen.profile.domainLayer

import kotlinx.coroutines.flow.FlowCollector
import vn.base.mvvm.core.base.usecase.BaseUseCase
import vn.base.mvvm.core.base.usecase.None
import vn.base.mvvm.screen.profile.data.local.UserInfo
import vn.base.mvvm.core.delivery.Result
import vn.base.mvvm.screen.profile.dataLayer.repo.RepositoryProfile

class UseCaseMyInformation(
    private val repository: RepositoryProfile
) : BaseUseCase<UserInfo, None>() {

    override suspend fun FlowCollector<Result<UserInfo>>.run(params: None) {
        emit(repository.myInformation())
    }
}