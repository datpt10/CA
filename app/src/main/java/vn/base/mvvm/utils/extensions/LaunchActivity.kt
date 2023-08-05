package vn.base.mvvm.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Kotlin Extensions for simpler, easier and funw way
 * of launching of Activities
 * https://gist.github.com/wajahatkarim3/d3a728dbb20002dc54ac44bad40e4077
 */

inline fun <reified T : Any> Activity.launchActivity (
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {})
{
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> Context.launchActivity (
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {})
{
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)
