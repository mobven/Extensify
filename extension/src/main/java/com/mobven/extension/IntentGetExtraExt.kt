package com.mobven.extension

import android.content.Intent

fun Intent.getIntExtra(name: String): Int? {
    val value = getIntExtra(name, Int.MIN_VALUE)
    if (value == Int.MIN_VALUE) {
        return null
    }
    return value
}

fun Intent.getFloatExtra(name: String): Float? {
    val value = getFloatExtra(name, Float.MIN_VALUE)
    if (value == Float.MIN_VALUE) {
        return null
    }
    return value
}

fun Intent.getLongExtra(name: String): Long? {
    val value = getLongExtra(name, Long.MIN_VALUE)
    if (value == Long.MIN_VALUE) {
        return null
    }
    return value
}

fun Intent.getDoubleExtra(name: String): Double? {
    val value = getDoubleExtra(name, Double.MIN_VALUE)
    if (value == Double.MIN_VALUE) {
        return null
    }
    return value
}