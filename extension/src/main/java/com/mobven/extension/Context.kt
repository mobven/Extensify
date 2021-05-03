package com.mobven.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.Toast
import androidx.core.content.ContextCompat

/**
 * Extension method to show toast for Context.
 */
fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, text, duration).show() }

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

/**
 * Extension method to browse for Context.
 */
fun Context.browse(url: String): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Extension method to rate app on PlayStore for Context.
 */
fun Context.market(): Boolean {
    return if (browse("market://details?id=$packageName")) {
        true
    } else {
        browse("http://play.google.com/store/apps/details?id=$packageName")
    }
}

/**
 * Extension method to share for Context.
 */
fun Context.share(text: String, subject: String = ""): Boolean {
    val intent = Intent()
    intent.type = "text/plain"
    intent.putExtra(EXTRA_SUBJECT, subject)
    intent.putExtra(EXTRA_TEXT, text)
    return try {
        startActivity(createChooser(intent, null))
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

/**
 * Extension method to send email for Context.
 */
fun Context.email(email: String, subject: String = "", text: String = ""): Boolean {
    val intent = Intent(ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(EXTRA_EMAIL, arrayOf(email))
        if (subject.isNotBlank()) putExtra(EXTRA_SUBJECT, subject)
        if (text.isNotBlank()) putExtra(EXTRA_TEXT, text)
    }
    return try {
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

/**
 * Extension method to make call for Context.
 */
fun Context.makeCall(number: String): Boolean {
    return try {
        val intent = Intent(ACTION_CALL, Uri.parse("tel:$number"))
        startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Extension method to Send SMS for Context.
 */
fun Context.sendSms(number: String, text: String = ""): Boolean {
    return try {
        val intent = Intent(ACTION_VIEW, Uri.parse("sms:$number")).apply {
            putExtra("sms_body", text)
        }
        startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Extension method to dail telephone number for Context.
 */
fun Context.dial(tel: String?) = startActivity(Intent(ACTION_DIAL, Uri.parse("tel:$tel")))

/**
 * Extension method to send sms for Context.
 */
fun Context.sms(phone: String?, body: String = "") {
    val smsToUri = Uri.parse("smsto:$phone")
    val intent = Intent(ACTION_SENDTO, smsToUri)
    intent.putExtra("sms_body", body)
    startActivity(intent)
}

/**
 * Extension method to start activity with Intent extras
 */
fun <T> Context.startActivityExtras(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}








