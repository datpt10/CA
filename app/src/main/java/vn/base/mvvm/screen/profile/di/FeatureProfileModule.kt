package vn.base.mvvm.screen.profile.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.base.mvvm.screen.profile.presentation.ProfileViewModel

val featureProfileModule = module {
    viewModel{ ProfileViewModel(get()) }
}