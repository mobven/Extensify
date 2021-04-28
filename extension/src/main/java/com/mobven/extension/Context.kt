package com.mobven.extension

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.Toast
import androidx.core.content.ContextCompat

/**
 * Extension method to show toast for Context.
 */
fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = this?.let { Toast.makeText(it, text, duration).show() }

/**
 * Extension method to get height of screen
 */
fun Activity.heightPixels(): Int {
    val outMetrics = DisplayMetrics()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        display?.getRealMetrics(outMetrics)
    } else {
        @Suppress("DEPRECATION")
        val display = windowManager.defaultDisplay
        @Suppress("DEPRECATION")
        display.getMetrics(outMetrics)
    }

    return outMetrics.heightPixels
}

/**
 * Extension method to get width of screen
 */
fun Activity.widthPixels(): Int {
    val outMetrics = DisplayMetrics()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        display?.getRealMetrics(outMetrics)
    } else {
        @Suppress("DEPRECATION")
        val display = windowManager.defaultDisplay
        @Suppress("DEPRECATION")
        display.getMetrics(outMetrics)
    }

    return outMetrics.widthPixels
}

/**
 * Extension method to get drawable from resource
 */
fun Context.drawable(res: Int): Drawable? = ContextCompat.getDrawable(this, res)

/**
 * Extension method to provide simpler access to {@link ContextCompat#getColor(int)}.
 */
fun Context.color(color: Int) = ContextCompat.getColor(this, color)

/**
 * Extension method to convert dp to px
 */
fun Context.dpToPixels(dp: Float): Int {
    val metrics = resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
}


