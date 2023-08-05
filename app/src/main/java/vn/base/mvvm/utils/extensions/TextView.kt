package vn.base.mvvm.utils.extensions

import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView

fun TextView.handlerLink(
    underLine: Boolean? = false,
    onLinkClicked: ((url: String) -> Unit?)? = null
) {
    val s: Spannable = SpannableString(text)
    val spans = s.getSpans(0, s.length, URLSpan::class.java)
    for (span in spans) {
        val start = s.getSpanStart(span)
        val end = s.getSpanEnd(span)
        s.removeSpan(span)
        val spanNew = URLSpanNoUnderline(span.url, onLinkClicked, underLine)
        s.setSpan(spanNew, start, end, 0)
    }
    if (movementMethod == null || movementMethod !is LinkMovementMethod) {
        movementMethod = LinkMovementMethod.getInstance()
    }
    text = s
}

private class URLSpanNoUnderline(
    url: String?,
    val onLinkClicked: ((url: String) -> Unit?)? = null,
    val underLine: Boolean? = false
) : URLSpan(url) {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = underLine ?: false
    }

    override fun onClick(widget: View) {
        onLinkClicked?.invoke(url) ?: run {
            super.onClick(widget)
        }
    }
}

@Suppress("DEPRECATION")
fun TextView.fromHtml(html: String?) {
    this.text = when {
        html == null -> {
            SpannableString("")
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        }
        else -> {
            Html.fromHtml(html)
        }
    }
}