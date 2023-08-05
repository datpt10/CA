package vn.base.mvvm.screen.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import vn.base.mvvm.core.base.usecase.None
import vn.base.mvvm.screen.splash.domainLayer.SplashState
import vn.base.mvvm.screen.splash.domainLayer.UseCaseSplash
import vn.base.mvvm.screen.splash.domainLayer.VersionState
import vn.base.mvvm.core.delivery.Result

class SplashViewModel(
//    private val useCaseCheckVersion: UseCaseCheckVersion,
    private val useCaseSplash: UseCaseSplash,
) : ViewModel() {

    private val _splashState = MutableStateFlow<Result<SplashState>?>(null)
    val splashState = _splashState.asStateFlow()

    private val _stateVersion = MutableStateFlow<Result<VersionState>?>(null)
    val stateVersion = _stateVersion.asStateFlow()

    fun checkVersion() {
        viewModelScope.launch {
//            useCaseCheckVersion(None).collect { _stateVersion.value = it }
        }
    }

    init {
        initialize()
    }

    fun initialize() {
        viewModelScope.launch {
            val usSplash = useCaseSplash(None)
            usSplash.zip(usSplash) { _, splash ->
                splash
            }.collect {
                _splashState.value = it
            }
        }
    }
}