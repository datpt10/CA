package vn.base.mvvm.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.kongqw.network.monitor.NetworkMonitorManager
import com.kongqw.network.monitor.enums.NetworkState
import com.kongqw.network.monitor.interfaces.NetworkMonitor
import com.tapadoo.alerter.Alerter
import org.koin.android.ext.android.inject
import timber.log.Timber
import vn.base.mvvm.R
import vn.base.mvvm.core.base.BaseActivity
import vn.base.mvvm.utils.view.dialog.DialogUtil
import vn.base.mvvm.core.bus.EventBus
import vn.base.mvvm.core.bus.UI
import vn.base.mvvm.data.network.event.RefreshTokenFailed

class MainActivity : BaseActivity() {

    companion object {

    }

    private var colorAccent = Color.WHITE
    private var colorGray900 = Color.WHITE

    private val viewModel: MainViewModel by inject()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(vn.base.mvvm.ui.R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        colorAccent = ContextCompat.getColor(this, vn.base.mvvm.ui.R.color.accent)
        colorGray900 = ContextCompat.getColor(this, vn.base.mvvm.ui.R.color.gray_900)

        handlerObserver()
        setUI()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    /**
     * handler event:
     * 1. token refresh failed
     */
    private fun registerEvents() {
        EventBus.register(this.javaClass.simpleName, UI, RefreshTokenFailed::class.java) {
            viewModel.logout()
            DialogUtil.message(
                this,
                getString(vn.base.mvvm.ui.R.string.ui_common_notice),
                it.message ?: "Session expired. Please log in again."
            )
        }
    }

    //region permision

    @NetworkMonitor
    @Suppress("UNUSED")
    fun onNetWorkStateChange(networkState: NetworkState) {
        Timber.e("onNetWorkStateChange  networkState = $networkState")
        when (networkState) {
            NetworkState.NONE -> {
                Alerter.create(this@MainActivity)
                    .setText("connecting to server...")
                    .setBackgroundColorRes(vn.base.mvvm.ui.R.color.yellow)
                    .hideIcon()
                    .enableInfiniteDuration(true)
                    .enableProgress(true)
                    .disableOutsideTouch()
                    .setDismissable(false)
                    .show()
            }

            else -> {
                if (Alerter.isShowing)
                    Alerter.hide()
            }
        }
    }

    private fun setUI() {
        val decorView = window.decorView
        val flags = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = flags
    }

    private fun handlerObserver() {

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        NetworkMonitorManager.getInstance().register(this)
        Timber.d("onStart")
        registerEvents()
    }

    override fun onStop() {
        Timber.d("onStop")
        NetworkMonitorManager.getInstance().unregister(this)
        EventBus.unregister(this.javaClass.simpleName)
        super.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}