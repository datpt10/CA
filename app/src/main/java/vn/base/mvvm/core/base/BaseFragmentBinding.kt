package vn.base.mvvm.core.base

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import vn.base.mvvm.utils.extensions.navigation.safeNavigate
import vn.base.mvvm.utils.view.browser.launchUri

abstract class BaseFragmentBinding<T : ViewBinding>(private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> T) :
    Fragment() {

    companion object {
        private const val DELAY_MILLIS = 500L
        private var previousClickTimeMillis = 0L
    }

    private var _binding: T? = null

    // This can be accessed by the child fragments
    // Only valid between onCreateView and onDestroyView
    val binding: T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)

        // replaced _binding!! with binding
        return binding.root
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

    open fun addFlag(){
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    open fun clearFlag(){
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    @SuppressLint("InlinedApi")
    open fun hideSystemUi(view: View) {
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

    protected fun safeAction(action: () -> Unit) {
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis >= previousClickTimeMillis + DELAY_MILLIS) {
            previousClickTimeMillis = currentTimeMillis
            action()
        }
    }

    fun safeNavigate(
        @IdRes currentDestinationId: Int,
        @IdRes actionId: Int,
        bundle: Bundle? = null,
        options: NavOptions? = null
    ) {
        findNavController().safeNavigate(currentDestinationId, actionId, bundle, options)
    }

    fun safeNavigate(
        deepLink: Uri
    ) {
        if (deepLink.toString().startsWith("http", true)) {
            launchUri(activity, deepLink)
        } else {
            //open in-app screen
            try {
                findNavController().navigate(deepLink)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }


    // Removing the binding reference when not needed is recommended as it avoids memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}