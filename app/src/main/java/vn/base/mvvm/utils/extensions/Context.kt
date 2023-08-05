package vn.base.mvvm.utils.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

val Context.inputWindowManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

//check if network is connected
@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
fun Context.isNetworkAvailable() = run {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var isConnected = false
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
            isConnected = when {
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    } else
        connectivityManager.activeNetworkInfo?.let {
            isConnected = it.isConnected
        }
    isConnected
}

fun Context.openFacebookMessager(fanpageId: String = "108269057574237") {
    val LINK_MESSAGE_APP = "fb-messenger://user-thread/%s"
    val LINK_FANPAGE_BROWSED = "https://www.facebook.com/messages/t/%s"

    val uri =
        Uri.parse(
            try {
                applicationContext.packageManager.getPackageInfoCompat(
                    "com.facebook.orca",
                    PackageManager.GET_ACTIVITIES
                ).let {
                    // Installed
                    String.format(LINK_MESSAGE_APP, fanpageId)
                }
            } catch (ex: PackageManager.NameNotFoundException) {
                // not installed it will open your app directly on playstore
                String.format(LINK_FANPAGE_BROWSED, fanpageId)
            }
        )

    try {
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (ex: Exception) {//browser not found
        ex.printStackTrace()
    }
}

fun PackageManager.getPackageInfoCompat(packageName: String, flags: Int = 0): PackageInfo =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        @Suppress("DEPRECATION") getPackageInfo(packageName, flags)
    }


fun openDialer(code: String, context: Context) {
    try {
        val u = Uri.parse("tel:${code.replace("#", Uri.encode("#"))}")
        val intent = Intent(Intent.ACTION_DIAL, u)

        context.startActivity(intent)
    } catch (ex: Exception) {
        Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
    }
}