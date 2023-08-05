package vn.base.mvvm.di

import org.koin.dsl.module
import vn.base.mvvm.screen.profile.domainLayer.UseCaseMyInformation
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogin
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogoutRefreshToken
import vn.base.mvvm.screen.splash.domainLayer.UseCaseCheckVersion
import vn.base.mvvm.screen.splash.domainLayer.UseCaseSplash

val domainModule = module {
    //initialize
    factory { UseCaseSplash(get()) }

    //auth
    factory { UseCaseLogin(get()) }
    factory { UseCaseCheckVersion(get()) }
    factory { UseCaseLogoutRefreshToken(get()) }

    //splash

    // profile
    factory { UseCaseMyInformation(get()) }
}