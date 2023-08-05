package vn.base.mvvm.screen.splash.dataLayer.repo

import vn.base.mvvm.data.local.LocalStorage
import vn.base.mvvm.screen.profile.dataLayer.service.ServiceProfile
import vn.base.mvvm.screen.splash.domainLayer.SplashState
import vn.base.mvvm.screen.splash.dataLayer.service.ServiceSplash
import vn.base.mvvm.core.delivery.Result
import vn.base.mvvm.core.delivery.UnAuthorizedError
import vn.base.mvvm.screen.splash.domainLayer.VersionState

class RepositorySplashImpl(
    private val currentVersion: String,
    private val localStorage: LocalStorage,
    private val service: ServiceSplash,
    private val serviceProfile: ServiceProfile,
) : RepositorySplash {

    override suspend fun initial(): Result<SplashState> {
        //check login
        return Result.Success(when {
            localStorage.getAuthToken() != null ->
                when (val result = serviceProfile.myInformation()) {
                    is Result.Success -> result.successData.content?.let {
                        SplashState.OpenDashBoard
                    } ?: SplashState.OpenDashBoard

                    is Result.Failure -> if (result.reason is UnAuthorizedError) logoutState() else SplashState.OpenDashBoard
                    else -> SplashState.OpenDashBoard
                }

            else -> SplashState.OpenLogin
        })
    }

    private fun logoutState(): SplashState {
        localStorage.logout()
        return SplashState.OpenLogin
    }

    override suspend fun checkVersion(): Result<VersionState> =
        Result.Success(when (val resultUpdate = service.checkVersion(currentVersion)) {
            is Result.Success ->
                resultUpdate.successData.content?.let {
                    when {
                        it.version_information?.force == true ->
                            VersionState.ForceUpdate(it)

                        it.version_information?.popup == true ->
                            VersionState.OptionalUpdate(it)

                        else -> VersionState.LatestRelease
                    }
                } ?: VersionState.LatestRelease

            else -> VersionState.LatestRelease
        })
}