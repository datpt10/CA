package vn.base.mvvm.data.network

object ENDPOINT{
    const val APP_VERSION = "app-versions/latest"

    //auth
    const val AUTH_REFRESH_TOKEN = "auth/refresh-token"
    const val AUTH_CHECK_AVAILABLE_EMAIL = "moderator-auth/check-available-identity"
    const val AUTH_LOGIN = "auth/login"
    const val AUTH_LOGOUT = "auth/logout"
    const val AUTH_LOGOUT_REFRESH_TOKEN = "auth/logout/refresh-token"
    const val AUTH_FORGOT_PASS = "auth/forgot-password"
    const val AUTH_VERIFY_FORGET_PASS = "auth/verify-forgot-password-otp"
    const val AUTH_RESET_PASSWORD = "auth/reset-password"
    const val AUTH_SIGNUP = "auth/signup"
    const val AUTH_VERIFY_SIGNUP = "auth/verify-signup"

    //profile
    const val ME = "v1.1/me"

    //notification
    const val NOTIFICATION_STATS = "/v1.1/notifications/stats"

    //push
    const val PUSH_REGISTER = "/v1.1/push/register"
    const val PUSH_UNREGISTER = "/v1.1/push/unregister"
}