package vn.base.mvvm.screen.login.presentation

import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import vn.base.mvvm.core.base.BaseFragmentBinding
import vn.base.mvvm.data.local.LocalStorage
import vn.base.mvvm.databinding.LoginFragmentBinding
import vn.base.mvvm.screen.login.state.LoginUiState
import vn.base.mvvm.screen.login.state.StateLogin
import vn.base.mvvm.utils.extensions.hideProgressDialog
import vn.base.mvvm.utils.extensions.showProgressDialog
import vn.base.mvvm.utils.view.dialog.DialogUtil

class LoginFragment : BaseFragmentBinding<LoginFragmentBinding>(LoginFragmentBinding::inflate) {
    private val viewModel: LoginViewModel by inject()
    private val localStorage: LocalStorage by inject()

    override fun initView(view: View) {
    }

    override fun initListener() {

    }

    override fun onResume() {
        super.onResume()
        clearFlag()
    }

    override fun initObserve() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateLogin.collectLatest { uiState ->
                when (uiState) {
                    LoginUiState.Idle -> {}
                    LoginUiState.Loading -> {
                        showProgressDialog()
                    }

                    is LoginUiState.Error -> {
                        hideProgressDialog()
                        showMessage(message = uiState.reason.errorDesc)
                    }

                    is LoginUiState.Success -> {
                        onLoginSuccess(uiState.data)
                    }
                }
            }
        }
    }

    private fun onLoginSuccess(@Suppress("UNUSED_PARAMETER") stateLogin: StateLogin) {
        hideProgressDialog()
        when (stateLogin) {
            StateLogin.OpenBiometrics -> {}
            StateLogin.OpenChangePass -> {}
            StateLogin.OpenPermission -> {}
            StateLogin.OpenDashboard -> {}
        }
    }

    private fun showMessage(
        titleAlert: String? = getString(vn.base.mvvm.ui.R.string.ui_common_notice), message: String
    ) {
        context?.let {
            DialogUtil.message(it,
                titleAlert,
                message,
                getString(vn.base.mvvm.ui.R.string.ui_common_close),
                callback = { dialog, _ ->
                    dialog.dismiss()
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}