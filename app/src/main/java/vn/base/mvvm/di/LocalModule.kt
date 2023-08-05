package vn.base.mvvm.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.core.qualifier.named
import org.koin.dsl.module
import vn.base.mvvm.data.local.DeviceManager
import vn.base.mvvm.data.local.LocalStorage

val localModule = module {
    single<SharedPreferences> {
        get<Context>().getSharedPreferences(
            "kotlincodes",
            Context.MODE_PRIVATE
        )
    }

    single { DeviceManager(get()) }

    single { LocalStorage(get(), get(), get(qualifier = named("GlobalScope"))) }
}