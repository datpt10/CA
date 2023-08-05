package vn.base.mvvm.screen.profile.data.local

import java.io.Serializable

data class UserInfo(
    val userName: String,
    val fullName: String,
    val avatar: String,
    val roles: String = Role.maker.toString(),
    val nextAction: String = NextActionType.default.toString(),
    val typeView: String = TypeViewAction.default.toString()
) : Serializable

enum class Role : Serializable {
    maker,
    checker,
    approver
}

enum class NextActionType {
    changePass,
    verifyIdentify,
    changeDevice,
    default
}

enum class TypeViewAction {
    changePassword,
    permissionBiometric,
    verifyIdentify,
    dashBoard,
    infoCancelSOTP,
    sentCancelSOTP,
    default
}