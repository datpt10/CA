package vn.base.mvvm.screen.profile.di

import org.koin.dsl.module
import retrofit2.Retrofit
import vn.base.mvvm.screen.profile.dataLayer.ApiProfile
import vn.base.mvvm.screen.profile.dataLayer.service.ServiceProfile

val networkProfileModule = module {

    factory { get<Retrofit>().create(ApiProfile::class.java) }

    factory { ServiceProfile(get()) }
}