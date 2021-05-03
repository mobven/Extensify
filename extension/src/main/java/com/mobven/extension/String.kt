package com.mobven.extension

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import java.util.*

/**
 * Extension method for change color of spannable string
 */
fun SpannableString.setColor(color: Int, start: Int, end: Int): SpannableString {
    this.setSpan(ForegroundColorSpan(color), start, end, 0)
    return this
}

/**
 * Extension method for change typeface of spannable string
 */
fun SpannableString.bold(start: Int, end: Int): SpannableString {
    this.setSpan(StyleSpan(Typeface.BOLD), start, end, 0)
    return this
}

/**
 * Extension method for change typeface of spannable string
 */
fun SpannableString.underline(start: Int, end: Int): SpannableString {
    this.setSpan(UnderlineSpan(), start, end, 0)
    return this
}

/**
 * Extension method for change typeface of spannable string
 */
fun SpannableString.italic(start: Int, end: Int): SpannableString {
    this.setSpan(StyleSpan(Typeface.ITALIC), start, end, 0)
    return this
}

/**
 * Extension method for capitalize all of the words in a [String]
 * @param locale is to determine language if word has a different character set from app
 * @param delimiter is to determine delimiter for each word
 * @sample "çay erdal bakkalda içilir".capitalizeWords(Locale.forLanguageTag("TR")) returns "Çay Erdal Bakkalda İçilir"
 * @sample "çay-şeker".capitalizeWords(Locale.forLanguageTag("TR"), "-") returns "Çay-Şeker"
 */
fun String.capitalizeWords(locale: Locale = Locale.ROOT, delimiter: String = " "): String {
    return split(delimiter).joinToString(delimiter) {
        it.toLowerCase(locale).capitalize(locale)
    }
}
