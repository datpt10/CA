package vn.base.mvvm.utils.extensions

import android.util.Log
import vn.base.mvvm.utils.extensions.common.Constants.EMPTY

inline fun <reified T> T.logD(message: String = EMPTY) {
    Log.d(T::class.java.simpleName, message)
}

inline fun <reified T> T.logD(tag: String, message: String = EMPTY) {
    Log.d(tag, message)
}

inline fun <reified T> T.logE(message: String? = null, throwable: Throwable? = null) {
    Log.e(T::class.java.simpleName, message, throwable)
}

inline fun <reified T> T.logE(
    tag: String? = null,
    message: String? = null,
    throwable: Throwable? = null
) {
    if (tag.isNullOrEmpty()) {
        Log.e(T::class.java.simpleName, message, throwable)
    } else {
        Log.e(tag, message, throwable)
    }
}

inline fun <reified T> T.logDm(message: String? = EMPTY) {
    when {
        message.isNullOrEmpty() -> Log.d("lovingo", EMPTY)
        else -> Log.d("lovingo", message)
    }
}