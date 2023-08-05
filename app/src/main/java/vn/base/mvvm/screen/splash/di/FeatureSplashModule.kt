package vn.base.mvvm.screen.splash.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.base.mvvm.screen.splash.presentation.SplashViewModel

val featureSplashModule = module {

    viewModel { SplashViewModel(get()) }

}