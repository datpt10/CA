package vn.base.mvvm.screen.dashBoard.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import vn.base.mvvm.core.RETROFIT_NORMAL
import vn.base.mvvm.screen.dashBoard.dataLayer.ApiDashBoard
import vn.base.mvvm.screen.dashBoard.dataLayer.service.ServiceDashBoard

val networkDashBoardModule = module {
    factory { get<Retrofit>(named(RETROFIT_NORMAL)).create(ApiDashBoard::class.java) }
    factory { ServiceDashBoard(get()) }
}