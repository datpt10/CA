package vn.base.mvvm.screen.login.state

sealed class StateLogin {
    object OpenChangePass : StateLogin()

    object OpenBiometrics : StateLogin()

    object OpenPermission : StateLogin()

    object OpenDashboard : StateLogin()
}
