package vn.base.mvvm.screen.splash.dataLayer.repo
import vn.base.mvvm.core.delivery.Result
import vn.base.mvvm.screen.splash.domainLayer.SplashState
import vn.base.mvvm.screen.splash.domainLayer.VersionState

interface RepositorySplash {
    suspend fun initial(): Result<SplashState>

    suspend fun checkVersion(): Result<VersionState>
}