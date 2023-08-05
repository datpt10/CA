package vn.base.mvvm.utils.view.browser

import android.app.Activity
import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.*
import androidx.core.graphics.drawable.toBitmap
import java.lang.Exception

class ChromeCustomTab(private val activity: Activity, private val url: String) {
    private var customTabsSession: CustomTabsSession? = null
    private var client: CustomTabsClient? = null
    private var connection: CustomTabsServiceConnection? = null
    private var warmupWhenReady = false
    private var mayLaunchWhenReady = false

    fun warmup() {
        if (client != null) {
            warmupWhenReady = false
            client!!.warmup(0)
        } else {
            warmupWhenReady = true
        }
    }

    fun mayLaunch() {
        val session = getSession()
        if (client != null) {
            mayLaunchWhenReady = false
            session!!.mayLaunchUrl(Uri.parse(url), null, null)
        } else {
            mayLaunchWhenReady = true
        }
    }

    fun show(
        @ColorInt color: Int,
        @DrawableRes closeIcon: Int? = null,
        isLight: Boolean = true,
        showTitle: Boolean = true
    ) {
        val builder = CustomTabsIntent.Builder(getSession())
        val param = CustomTabColorSchemeParams.Builder()
            .setNavigationBarColor(color)
            .setToolbarColor(color)
            .setSecondaryToolbarColor(color)
            .build()
        builder.setDefaultColorSchemeParams(param)
        builder.setColorScheme(if (isLight) CustomTabsIntent.COLOR_SCHEME_LIGHT else CustomTabsIntent.COLOR_SCHEME_DARK)
        builder.setShowTitle(showTitle)
        closeIcon?.let {
            AppCompatResources.getDrawable(
                activity,
                closeIcon
            )?.let { drawable ->
                builder.setCloseButtonIcon(drawable.toBitmap())
            }
        }
        val customTabsIntent = builder.build()
        try {
            customTabsIntent.launchUrl(activity, Uri.parse(url))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun getSession(): CustomTabsSession? {
        if (client == null) {
            customTabsSession = null
        } else if (customTabsSession == null) {
            customTabsSession = client!!.newSession(object : CustomTabsCallback() {
                override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {
                    Log.w("CustomTabs", "onNavigationEvent: Code = $navigationEvent")
                }
            })
        }
        return customTabsSession
    }

    private fun bindCustomTabsService() {
        if (client != null) {
            return
        }
        connection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(
                name: ComponentName,
                client: CustomTabsClient
            ) {
                this@ChromeCustomTab.client = client
                if (warmupWhenReady) {
                    warmup()
                }
                if (mayLaunchWhenReady) {
                    mayLaunch()
                }
            }

            override fun onServiceDisconnected(name: ComponentName) {
                client = null
            }
        }
        val ok =
            CustomTabsClient.bindCustomTabsService(
                activity, CUSTOM_TAB_PACKAGE_NAME,
                connection as CustomTabsServiceConnection
            )
        if (!ok) {
            connection = null
        }
    }

    fun unbindCustomTabsService() {
        if (connection == null) {
            return
        }
        try {
            activity.unbindService(connection!!)
        }catch(ex: Exception){
            ex.printStackTrace()
        }

        client = null
        customTabsSession = null
    }

    companion object {
        const val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome"
    }

    init {
        bindCustomTabsService()
    }
}