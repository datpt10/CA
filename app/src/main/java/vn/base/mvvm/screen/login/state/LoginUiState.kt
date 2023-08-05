package vn.base.mvvm.screen.login.state

import vn.base.mvvm.core.delivery.Reason

sealed class LoginUiState {
    object Idle : LoginUiState()

    object Loading : LoginUiState()

    data class Error(val reason: Reason) : LoginUiState()

    data class Success(val data: StateLogin) : LoginUiState()
}