package vn.base.mvvm.utils.extensions

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import vn.base.mvvm.utils.view.dialog.ProgressDialogUtil
import java.io.Serializable

fun Fragment.inflatView(@LayoutRes layoutId: Int) = layoutInflater.inflate(layoutId, null)

fun Fragment.showProgressDialog() {
    context?.let { context ->
        ProgressDialogUtil.show(
            context
        )
    }
}

@SuppressLint("SuspiciousIndentation")
fun Fragment.hideProgressDialog() {
    if (activity?.isDestroyed != true && activity?.isFinishing == true) return
        ProgressDialogUtil.dismiss()
}

fun Fragment.hideSoftKeyboard(flag: Int = 0) {
    view?.let { view ->
        view.context?.inputWindowManager?.run {
            if (isActive) {
                hideSoftInputFromWindow(view.windowToken, flag)
            }
        }
    }
}

inline fun <reified T : Any> Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else default
}

inline fun <reified T : Any> Fragment.extraNotNull(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    requireNotNull(if (value is T) value else default) { key }
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}


fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.get<T>(key)

fun <T> Fragment.getNavigationResultLiveData(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}