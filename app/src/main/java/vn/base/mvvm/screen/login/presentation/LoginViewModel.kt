package vn.base.mvvm.screen.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import vn.base.mvvm.core.delivery.onResultHandle
import vn.base.mvvm.screen.login.domainLayer.UseCaseLogin
import vn.base.mvvm.screen.login.state.LoginUiState

class LoginViewModel(val useCaseLogin: UseCaseLogin) : ViewModel() {

    private val _state = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val stateLogin: SharedFlow<LoginUiState> get() = _state

    fun login(userName: String, password: String, times: String) {
        viewModelScope.launch {
            useCaseLogin(UseCaseLogin.Params(userName, password, times)).collect {
                it.onResultHandle({
                    _state.value = LoginUiState.Loading
                }, { reason ->
                    _state.value = LoginUiState.Error(reason)
                }, { state ->
                    _state.value = LoginUiState.Success(state)
                })
            }
        }
    }
}