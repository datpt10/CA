package vn.base.mvvm.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.base.mvvm.data.local.LocalStorage

class MainViewModel(
    private val localStorage: LocalStorage,
) : ViewModel() {

    init {

    }


    fun logout() {
        viewModelScope.launch {
        }
    }
}