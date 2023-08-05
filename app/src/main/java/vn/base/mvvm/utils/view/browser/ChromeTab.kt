package vn.base.mvvm.utils.view.browser

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.graphics.drawable.toBitmap
import vn.base.mvvm.ui.R

fun launchUri(
    context: Context?, uri: Uri?,
    @ColorInt color: Int? = Color.WHITE,
    @DrawableRes closeIcon: Int? = R.drawable.ic_arrow_back_black,
    isLight: Boolean = true,
    showTitle: Boolean = true
) {
    context?.let {
        val launched = if (Build.VERSION.SDK_INT >= 30) launchNativeApi30(
            context,
            uri
        ) else launchNativeBeforeApi30(
            context, uri!!
        )
        if (!launched) {
            try {
                val builder = CustomTabsIntent.Builder()

                color?.let {
                    val param = CustomTabColorSchemeParams.Builder()
                        .setNavigationBarColor(color)
                        .setToolbarColor(color)
                        .setSecondaryToolbarColor(color)
                        .build()
                    builder.setDefaultColorSchemeParams(param)
                }
                builder.setColorScheme(if (isLight) CustomTabsIntent.COLOR_SCHEME_LIGHT else CustomTabsIntent.COLOR_SCHEME_DARK)
                builder.setShowTitle(showTitle)
                closeIcon?.let {
                    AppCompatResources.getDrawable(
                        context,
                        closeIcon
                    )?.let { drawable ->
                        builder.setCloseButtonIcon(drawable.toBitmap())
                    }
                }
                builder.build().launchUrl(context, uri!!)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun launchNativeApi30(context: Context, uri: Uri?): Boolean {
    val nativeAppIntent = Intent(Intent.ACTION_VIEW, uri)
        .addCategory(Intent.CATEGORY_BROWSABLE)
        .addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
        )
    return try {
        context.startActivity(nativeAppIntent)
        true
    } catch (ex: ActivityNotFoundException) {
        false
    }
}

private fun launchNativeBeforeApi30(context: Context, uri: Uri): Boolean {
    val pm = context.packageManager

    // Get all Apps that resolve a generic url
    val browserActivityIntent = Intent()
        .setAction(Intent.ACTION_VIEW)
        .addCategory(Intent.CATEGORY_BROWSABLE)
        .setData(Uri.fromParts("http", "", null))
    val genericResolvedList: Set<String> = extractPackageNames(
        pm.queryIntentActivities(browserActivityIntent, 0)
    )

    // Get all apps that resolve the specific Url
    val specializedActivityIntent = Intent(Intent.ACTION_VIEW, uri)
        .addCategory(Intent.CATEGORY_BROWSABLE)
    val resolvedSpecializedList: MutableSet<String> = extractPackageNames(
        pm.queryIntentActivities(specializedActivityIntent, 0)
    ).toMutableSet()

    // Keep only the Urls that resolve the specific, but not the generic
    // urls.
    resolvedSpecializedList.removeAll(genericResolvedList)

    // If the list is empty, no native app handlers were found.
    if (resolvedSpecializedList.isEmpty()) {
        return false
    }

    // We found native handlers. Launch the Intent.
    specializedActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(specializedActivityIntent)
    return true
}

private fun extractPackageNames(resolveInfos: List<ResolveInfo>): Set<String> {
    val packageNameSet: MutableSet<String> = HashSet()
    for (ri in resolveInfos) {
        packageNameSet.add(ri.activityInfo.packageName)
    }
    return packageNameSet
}
