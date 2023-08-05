package vn.base.mvvm.screen.login.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.base.mvvm.screen.login.domainLayer.mapper.UserMapper
import vn.base.mvvm.screen.login.presentation.LoginViewModel

val featureLoginModule = module {

    viewModel { LoginViewModel(get()) }

    factory { UserMapper(get()) }
}