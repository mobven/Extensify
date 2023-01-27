package com.mobven.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Extension method that converts date for given format
 */
fun Date.formatToViewTime(customFormat: String = "dd MMMM yyyy"): String {
    val sdf = SimpleDateFormat(customFormat, Locale.getDefault())
    Calendar.getInstance().time
    return sdf.format(this)
}

/**
 * Add field date to current date
 * @param field:
 * Calendar.YEAR,
 * Calendar.MONTH,
 * Calendar.DAY_OF_MONTH or Calendar.DAY_OF_YEAR
 * Calendar.HOUR_OF_DAY,
 * Calendar.MINUTE,
 * Calendar.SECOND
 */
fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}