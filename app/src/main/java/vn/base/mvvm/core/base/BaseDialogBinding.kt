package vn.base.mvvm.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import vn.base.mvvm.utils.extensions.navigation.safeNavigate

abstract class BaseDialogBinding<T : ViewBinding>(private val inflateMethod : (LayoutInflater, ViewGroup?, Boolean) -> T) : DialogFragment(){

    companion object {
        private const val DELAY_MILLIS = 500L
        private var previousClickTimeMillis = 0L
    }

    private var _binding : T? = null

    // This can be accessed by the child fragments
    // Only valid between onCreateView and onDestroyView
    val binding : T get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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


    // Removing the binding reference when not needed is recommended as it avoids memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}