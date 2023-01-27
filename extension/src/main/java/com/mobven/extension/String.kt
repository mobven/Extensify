package com.mobven.extension

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import com.mobven.extension.definitions.CreditCardType
import java.text.DecimalFormat
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
    return split(delimiter).joinToString(delimiter) { word ->
        word.lowercase(locale)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
    }
}

/**
 *
 * Get the credit card type of given card number
 *
 */
fun String.getCreditCardIconType(): String {
    return when {
        matches(CreditCardType.AMEX.toRegex()) -> {
            CreditCardType.AMEX
        }
        matches(CreditCardType.MAESTRO.toRegex()) -> {
            CreditCardType.MAESTRO
        }
        matches(CreditCardType.VISA.toRegex()) -> {
            CreditCardType.VISA
        }
        matches(CreditCardType.TROY.toRegex()) -> {
            CreditCardType.TROY
        }
        matches(CreditCardType.MASTERCARD.toRegex()) -> {
            CreditCardType.MASTERCARD
        }
        else -> {
            CreditCardType.DEFAULT
        }
    }

}

fun String?.toPriceAmount(decimalFormat: DecimalFormat = DecimalFormat("###,###,###.##")): String? {
    return this?.let {
        if (it.isEmpty() || it.toDoubleOrNull() == null) return it
        decimalFormat.format(it.toDouble())
    }
}

/**
 * TC. kimlik numaraları 11 basamaklıdır ve ilk rakam 0 olamaz.
 * 1,3,5,7 ve 9.cu hanelerin toplamının 7 ile çarpımından 2,4,6, ve 8. haneler çıkartıldığında geriye kalan sayının 10'a göre modu 10. haneyi verir.
 * 1,2,3,4,5,6,7,8,9 ve 10. sayıların toplamının 10'a göre modu 11. rakamı verir.
 */
fun String?.isValidTCKN(): Boolean =
    this?.takeIf { length == 11 && get(0) != '0' }?.let {
        var oddSum = 0
        var evenSum = 0
        for (i in 0..8) {
            if (i % 2 == 0) oddSum += get(i).digitToInt()
            else evenSum += get(i).digitToInt()
        }
        val controlDigit = (oddSum * 7 - evenSum) % 10
        if (get(9).digitToInt() != controlDigit) {
            return false
        }
        return get(10).digitToInt() == (controlDigit + evenSum + oddSum) % 10
    } ?: run { false }

