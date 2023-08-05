package vn.base.mvvm.utils.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.clearEditTextColorfilter() {
    editText?.setOnFocusChangeListener { _, _ ->
        editText?.background?.clearColorFilter()
    }
    editText?.background?.clearColorFilter()
}