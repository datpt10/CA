package vn.base.mvvm.screen.profile.dataLayer.repo

import vn.base.mvvm.core.delivery.toResultData
import vn.base.mvvm.data.local.LocalStorage
import vn.base.mvvm.screen.profile.dataLayer.service.ServiceProfile

class RepositoryProfileImpl(
    private val serviceProfile: ServiceProfile,
    private val localStorage: LocalStorage
) : RepositoryProfile {

    override suspend fun myInformation() =
        serviceProfile.myInformation().toResultData {
            localStorage.saveProfile(it)
        }
}