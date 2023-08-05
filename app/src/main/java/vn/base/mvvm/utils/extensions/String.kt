package vn.base.mvvm.utils.extensions

import android.util.Patterns
import java.text.Normalizer
import java.util.regex.Pattern

fun String.isAlphabet(): Boolean = matches("[a-zA-Z]+( +[a-zA-Z]+)*".toRegex())

fun String.validPhone(): Boolean {
    return this.length >= 9 && !this.contains(" ") &&
            (matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*\$".toRegex())
                    || matches("^[+]?[\\d]+([\\-][\\d]+)*\\d\$".toRegex()))
}

fun String.validEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.validUrl()= Patterns.WEB_URL.matcher(this).matches()

fun deAccentEnglishCharacter(str: String): String {
    val nfdNormalizedString: String = Normalizer.normalize(str, Normalizer.Form.NFD)
    val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(nfdNormalizedString).replaceAll("")
        .replace("Đ", "D")
        .replace("đ", "d")
}