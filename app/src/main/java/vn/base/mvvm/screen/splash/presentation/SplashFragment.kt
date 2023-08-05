package vn.base.mvvm.screen.splash.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import vn.base.mvvm.R
import vn.base.mvvm.core.base.BaseFragmentBinding
import vn.base.mvvm.utils.extensions.launchWhenStarted
import vn.base.mvvm.core.delivery.onSuccess
import vn.base.mvvm.databinding.SplashFragmentBinding
import vn.base.mvvm.screen.splash.domainLayer.SplashState

class SplashFragment : BaseFragmentBinding<SplashFragmentBinding>(SplashFragmentBinding::inflate) {
    private val viewModel: SplashViewModel by inject()

    override fun initView(view: View) {
    }

    override fun initListener() {

    }

    override fun initObserve() {
        viewModel.splashState.onEach {
            it?.onSuccess { data ->
                delay(2000)
                stateSuccess(data)
            }
        }.launchWhenStarted(viewLifecycleOwner)
    }

    //region StateView
    private fun stateSuccess(state: SplashState) {
        when (state) {
            SplashState.OpenLogin ->
                safeNavigate(R.id.action_splashFragment_to_loginFragment)

            SplashState.OpenDashBoard -> {}
//                safeNavigate(R.id.action_splashFragment_to_homeFragment)

        }
    }

    private fun safeNavigate(
        @IdRes actionId: Int,
        bundle: Bundle? = null,
        options: NavOptions? = null
    ) {
        safeNavigate(R.id.splashFragment, actionId, bundle, options)
    }

}