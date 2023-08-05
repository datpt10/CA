package vn.base.mvvm

import android.app.Application
import androidx.lifecycle.*
import com.kongqw.network.monitor.NetworkMonitorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import vn.base.mvvm.di.appComponent

class BaseApplication : Application(), LifecycleEventObserver {
    // No need to cancel this scope as it'll be torn down with the process.
    // see https://medium.com/androiddevelopers/coroutines-patterns-for-work-that-shouldnt-be-cancelled-e26c40f142ad
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        NetworkMonitorManager.getInstance().init(this)

        startKoin {
            androidContext(this@BaseApplication)
            modules(appComponent(this@BaseApplication))
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Timber.e("onStateChanged= $event")
    }

    fun isForeground(): Boolean {
        val currentState = ProcessLifecycleOwner.get().lifecycle.currentState
        return (currentState == Lifecycle.State.STARTED
                || currentState == Lifecycle.State.RESUMED)
    }
}