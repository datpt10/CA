package vn.base.mvvm.screen.splash.di

import org.koin.dsl.module
import retrofit2.Retrofit
import vn.base.mvvm.screen.splash.dataLayer.ApiSplash
import vn.base.mvvm.screen.splash.dataLayer.service.ServiceSplash

val networkSplashModule = module {

    factory { get<Retrofit>().create(ApiSplash::class.java) }

    factory { ServiceSplash(get()) }
}