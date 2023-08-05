package vn.base.mvvm.di

import okhttp3.Interceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import vn.base.mvvm.core.OKHTTP_NORMAL
import vn.base.mvvm.core.RETROFIT_NORMAL
import vn.base.mvvm.core.VERSION_NAME
import vn.base.mvvm.core.retrofit.HeaderAuthenticationInterceptor
import vn.base.mvvm.core.retrofit.HeaderInterceptor
import vn.base.mvvm.core.retrofit.loggingInterceptor
import vn.base.mvvm.core.retrofit.okHttpClient
import vn.base.mvvm.core.retrofit.okHttpClientAuthentication
import vn.base.mvvm.core.retrofit.retrofit
import vn.base.mvvm.screen.dashBoard.di.networkDashBoardModule
import vn.base.mvvm.screen.login.di.networkAuthModule
import vn.base.mvvm.screen.profile.di.networkProfileModule
import vn.base.mvvm.screen.splash.di.networkSplashModule

fun createNetworkModule(baseUrl: String, isDebug: Boolean = false) = arrayOf(
    //region retrofit base module
    module {
        single<Interceptor> {
            HeaderAuthenticationInterceptor(
                versionName = get(qualifier = named(VERSION_NAME)),
                localStorage = get(),
                apiAuth = get()
            )
        }

        single<Interceptor>(qualifier = named(OKHTTP_NORMAL)) {
            HeaderInterceptor(
                versionName = get(qualifier = named(VERSION_NAME)), localStorage = get()
            )
        }

        single { loggingInterceptor(isDebug) }

        /**
         * AUTHENTICATION interceptor
         */
        single { okHttpClientAuthentication(get(), get()) }

        single { retrofit(baseUrl, get()) }

        /**
         * for refresh token only
         */
        single(named(OKHTTP_NORMAL)) { okHttpClient(get(qualifier = named(OKHTTP_NORMAL)), get()) }

        single(named(RETROFIT_NORMAL)) { retrofit(baseUrl, get(named(OKHTTP_NORMAL))) }
    },
    //endregion

    //region api service module
    networkAuthModule,
    networkSplashModule,
    networkProfileModule,
    networkDashBoardModule,
    //endregion
)