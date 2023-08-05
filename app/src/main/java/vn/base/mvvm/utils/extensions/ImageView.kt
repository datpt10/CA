package vn.base.mvvm.utils.extensions

import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.decoder.WebpFrameCacheStrategy
import com.bumptech.glide.integration.webp.decoder.WebpFrameLoader
import com.bumptech.glide.request.target.Target
import vn.base.mvvm.R

fun ImageView.loadAvatar(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.ic_avatar_default,
    @DrawableRes error: Int = R.drawable.ic_avatar_default
) {
    try {
        if (TextUtils.isEmpty(url))
            this.setImageResource(error)
        else
            Glide.with(this).load(url)
                .placeholder(placeholder)
                .error(error)
                .into(this)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholder: Int = vn.base.mvvm.ui.R.color.primary_10,
    @DrawableRes error: Int = vn.base.mvvm.ui.R.color.accent_10
) {
    try {
        if (TextUtils.isEmpty(url))
            this.setImageResource(error)
        else
            Glide.with(this).load(url)
                .placeholder(placeholder)
                .error(error)
                .into(this)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun ImageView.loadImageWebp(
    url: String?,
    @DrawableRes placeholder: Int = vn.base.mvvm.ui.R.color.primary_10,
    @DrawableRes error: Int = vn.base.mvvm.ui.R.color.accent_10
) {
    try {
        if (TextUtils.isEmpty(url))
            this.setImageResource(error)
        else
            Glide.with(this).load(url)
                .set(WebpFrameLoader.FRAME_CACHE_STRATEGY, WebpFrameCacheStrategy.AUTO)
                .placeholder(placeholder)
                .error(error)
                .into(this)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun ImageView.loadImageOriginal(
    url: String?,
    @DrawableRes placeholder: Int = vn.base.mvvm.ui.R.color.primary_10,
    @DrawableRes error: Int = vn.base.mvvm.ui.R.color.accent_10,
) {
    try {
        if (TextUtils.isEmpty(url))
            this.setImageResource(error)
        else {
            Glide.with(this).load(url)
                .placeholder(placeholder)
                .error(error)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .dontTransform()
                .into(this)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}