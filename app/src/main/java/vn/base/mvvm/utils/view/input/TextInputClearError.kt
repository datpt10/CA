package vn.base.mvvm.utils.view.input

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import com.google.android.material.textfield.TextInputLayout


class TextInputClearError : TextInputLayout {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        clearEditTextColorfilter()
    }

    override fun setError(@Nullable error: CharSequence?) {
        super.setError(error)
        clearEditTextColorfilter()
    }

    private fun clearEditTextColorfilter() {
        editText?.background?.clearColorFilter()
    }
}

