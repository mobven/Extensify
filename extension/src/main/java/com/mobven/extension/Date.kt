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