package vn.base.mvvm.data.local

import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import vn.base.mvvm.screen.profile.data.local.UserInfo
import java.util.UUID

class LocalStorage(
    private val sharedPreferences: SharedPreferences,
    private val deviceManager: DeviceManager,
    externalScope: CoroutineScope
) {

    // profile
    private val _profileUpdates: MutableStateFlow<UserInfo?> = MutableStateFlow(getProfile())
    val profileFlow: StateFlow<UserInfo?> get() = _profileUpdates

    companion object {
        const val TAG = "LocalStorage"

        const val USER_INFO = "USER_INFO"

        const val AUTH_TOKEN = "AUTH_TOKEN"
        const val AUTH_REFRESH_TOKEN = "AUTH_REFRESH_TOKEN"

        const val DEVICE_ID = "device_id"
        const val FIRST_LOGIN = "FIRST_LOGIN"

        const val FIRST_INSTALL = "FIRST_INSTALL"
        const val FIRST_TIME_OPEN_APP = "FIRST_TIME_OPEN_APP"
    }

    /**
     * User Info
     */
    fun saveProfile(userInfo: UserInfo) {
        sharedPreferences.edit()
            .putString(USER_INFO, Gson().toJson(userInfo))
            .apply()

        _profileUpdates.value = userInfo
    }

    fun getProfile(): UserInfo? {
        val userSaved = sharedPreferences.getString(USER_INFO, "")
        if (!TextUtils.isEmpty(userSaved)) {
            return Gson().fromJson(userSaved, UserInfo::class.java)
        }
        return null
    }

    /**
     * Auth token
     */
    fun saveAuthToken(authToken: String, refreshToken: String) {
        sharedPreferences.edit()
            .putString(AUTH_TOKEN, authToken)
            .putString(AUTH_REFRESH_TOKEN, refreshToken)
            .apply()
    }

    fun  getAuthToken() = sharedPreferences.getString(AUTH_TOKEN, null)

    /**
     * get device id
     */
    fun getDeviceId() = sharedPreferences.getString(DEVICE_ID, null) ?: run {
        val deviceId = deviceManager.getDevicesIds() ?: UUID.randomUUID().toString()
        sharedPreferences.edit()
            .putString(DEVICE_ID, deviceId)
            .apply()
        deviceId
    }

    /**
     * Mở app lần đầu
     */
    fun firstOpened(isFirst: Boolean = false) {
        sharedPreferences.edit().putBoolean(FIRST_TIME_OPEN_APP, isFirst).apply()
    }

    fun isFirstOpen(): Boolean {
        //phrase 1
        val isFirstOpenApp = sharedPreferences.getBoolean(FIRST_INSTALL, false)

        return if (isFirstOpenApp) false
        else sharedPreferences.getBoolean(FIRST_TIME_OPEN_APP, true)
    }


    /**
     * this func will clear all data stored
     */
    fun clear() = sharedPreferences.edit().clear().apply()

    fun logout() = sharedPreferences.edit().clear().apply()
}