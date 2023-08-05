package vn.base.mvvm.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import vn.base.mvvm.core.VERSION_NAME
import vn.base.mvvm.screen.dashBoard.dataLayer.repo.RepositoryDashboard
import vn.base.mvvm.screen.dashBoard.dataLayer.repo.RepositoryDashboardImpl
import vn.base.mvvm.screen.login.dataLayer.repo.RepositoryAuth
import vn.base.mvvm.screen.login.dataLayer.repo.RepositoryAuthImpl
import vn.base.mvvm.screen.profile.dataLayer.repo.RepositoryProfile
import vn.base.mvvm.screen.profile.dataLayer.repo.RepositoryProfileImpl
import vn.base.mvvm.screen.splash.dataLayer.repo.RepositorySplash
import vn.base.mvvm.screen.splash.dataLayer.repo.RepositorySplashImpl

val repositoryModule = module {
    factory<RepositorySplash> {
        RepositorySplashImpl(
            get(named(VERSION_NAME)),
            get(),
            get(),
            get()
        )
    }
    factory<RepositoryAuth> { RepositoryAuthImpl(get(), get(), get()) }
    factory<RepositoryProfile> { RepositoryProfileImpl(get(), get()) }
    factory<RepositoryDashboard> { RepositoryDashboardImpl(get()) }
}