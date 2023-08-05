package vn.base.mvvm.core.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import vn.base.mvvm.utils.extensions.navigation.safeNavigate

abstract class BaseFragment : Fragment() {
    companion object {
        private const val DELAY_MILLIS = 500L
        private var previousClickTimeMillis = 0L
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserve()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSavedState(savedInstanceState)
        initView(view)
        initListener()
    }

    open fun handleSavedState(savedInstanceState: Bundle?) {}

    abstract fun initView(view: View)

    abstract fun initListener()

    abstract fun initObserve()

    @SuppressLint("InlinedApi")
    open fun hideSystemUi(view: View) {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(
            requireActivity().window,
            view
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    open fun showSystemUI(){
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    protected fun safeAction(action: () -> Unit) {
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis >= previousClickTimeMillis + DELAY_MILLIS) {
            previousClickTimeMillis = currentTimeMillis
            action()
        }
    }

    open fun safeNavigate(
        @IdRes currentDestinationId: Int,
        @IdRes actionId: Int,
        bundle: Bundle? = null,
        options: NavOptions? = null
    ) {
        findNavController().safeNavigate(currentDestinationId, actionId, bundle, options)
    }
}