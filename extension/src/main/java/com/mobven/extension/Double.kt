package com.mobven.extension

import java.lang.IllegalArgumentException
import java.text.NumberFormat
import java.util.*

/**
 * Return double value or zero if it is null
 */
fun Double?.orZero(): Double = this ?: 0.0

/**
 * Convert double to number with given locale currency
 */
fun Double?.localizedNumberFormat(loc: Locale = Locale.getDefault()): String {
    try {
        val nf = NumberFormat.getCurrencyInstance(loc)
        if (this == 0.0) {
            return "0"
        }
        return nf.format(this)
    } catch (ex: IllegalArgumentException) {
        return "0"
    }
}