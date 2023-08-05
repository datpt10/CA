package vn.base.mvvm.di

import android.content.Context
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import vn.base.mvvm.BaseApplication
import vn.base.mvvm.BuildConfig
import vn.base.mvvm.activity.MainViewModel
import vn.base.mvvm.core.VERSION_NAME
import vn.base.mvvm.screen.dashBoard.di.featureDashBoardModule
import vn.base.mvvm.screen.login.di.featureLoginModule
import vn.base.mvvm.screen.profile.di.featureProfileModule
import vn.base.mvvm.screen.splash.di.featureSplashModule

fun appComponent(context: Context) = listOf(
    module {
        factory(qualifier = named(VERSION_NAME)) { BuildConfig.VERSION_NAME }

//        factory(qualifier = named(DEFAULT_WEB_CLIENT_ID)) { context.getString(R.string.default_web_client_id) }

        factory(qualifier = named("GlobalScope")) { (context as BaseApplication).applicationScope }

        viewModel {
            MainViewModel(get())
        }

        single { LocalBroadcastManager.getInstance(get()) }
    },
    *createNetworkModule(BuildConfig.BASE_URL, BuildConfig.DEBUG),
    domainModule,
    localModule,
    repositoryModule,

    featureDashBoardModule,
    featureSplashModule,
    featureLoginModule,
    featureProfileModule,
)