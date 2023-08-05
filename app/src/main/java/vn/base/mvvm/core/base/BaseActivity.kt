package vn.base.mvvm.core.base

import android.content.Context
import android.content.res.Resources
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity {
    constructor(): super()

    constructor(@LayoutRes contentLayoutId: Int): super(contentLayoutId)

    //region fix font size scale = 1
    override fun getResources(): Resources {
        val res = super.getResources()

        val config = res.configuration

        if (config.fontScale > 1F) {
            config.fontScale = 1f
            return createConfigurationContext(config).resources
        }
        return res
    }

    override fun attachBaseContext(newBase: Context?) {
        val context = newBase?.let { context ->
            val config = context.resources.configuration
            if (config.fontScale > 1F) {
                config.fontScale = 1f
                context.createConfigurationContext(config)
            } else context
        }

        super.attachBaseContext(context)
    }
    //endregion
}