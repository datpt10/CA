package vn.base.mvvm.screen.login.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import vn.base.mvvm.core.RETROFIT_NORMAL
import vn.base.mvvm.screen.login.dataLayer.ApiAuth
import vn.base.mvvm.screen.login.dataLayer.service.ServiceAuth

val networkAuthModule = module {

    factory { get<Retrofit>(named(RETROFIT_NORMAL)).create(ApiAuth::class.java) }

    factory { ServiceAuth(get()) }
}