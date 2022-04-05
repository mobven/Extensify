package com.mobven.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowMetrics
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
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
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = windowManager.maximumWindowMetrics
        val bounds: Rect = windowMetrics.bounds
        bounds.height()
    } else {
        val outMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        val display = windowManager.defaultDisplay
        @Suppress("DEPRECATION")
        display.getMetrics(outMetrics)
        outMetrics.heightPixels
    }
}

/**
 * Extension method to get width of screen
 */
fun Activity.widthPixels(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = windowManager.maximumWindowMetrics
        val bounds: Rect = windowMetrics.bounds
        bounds.width()
    } else {
        val outMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        val display = windowManager.defaultDisplay
        @Suppress("DEPRECATION")
        display.getMetrics(outMetrics)
        outMetrics.widthPixels
    }
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
        val intent = Intent(ACTION_VIEW)
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
fun <T> Context.startActivityWithExtras(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

/**
 * Shows url on Chrome Custam Tabs
 */
fun Context.showUrlOnCustomTabs(
    url: String,
    shareState: Int = CustomTabsIntent.SHARE_STATE_OFF,
    navigationColor: Int = android.R.color.holo_green_dark,
    toolbarColor: Int = android.R.color.holo_blue_bright
) {
    try {
        CustomTabsIntent.Builder().apply {
            val params = CustomTabColorSchemeParams.Builder().apply {
                setNavigationBarColor(color(navigationColor))
                setToolbarColor(color(toolbarColor))
            }
            setShareState(shareState)
                .setDefaultColorSchemeParams(params.build())
                .build()
                .launchUrl(this@showUrlOnCustomTabs, Uri.parse(url))
        }
    } catch (e: Exception) {
        browse(url)
    }
}

/**
 * Launches a chooser for image or videos from gallery and returns list of choosed item URI's
 */
fun ComponentActivity.chooseFromGallery(callback: ActivityResultCallback<List<Uri?>>): ActivityResultLauncher<String> {
    return registerForActivityResult(ActivityResultContracts.GetMultipleContents(), callback)
}

/**
 * Launches system dialogs to ask user permissions and returns a result map
 */
fun ComponentActivity.requestPermissions(callback: ActivityResultCallback<Map<String,Boolean>>): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), callback)
}

/**
 * Check all permissions are granted
 */
fun Context.hasPermissions(vararg permissions: String): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

/**
 * Request all permissions at once
 */
fun Context.requestAllPermissions(activity: Activity, whyWeNeedThisPermission: (permission: String) -> Unit, allPermissionsGranted: () -> Unit, vararg permissions: String) {
    if (!hasPermissions(*permissions)) {
        permissions.forEach {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, it)) {
                whyWeNeedThisPermission(it)
            } else {
                ActivityCompat.requestPermissions(activity, permissions, 100)
            }
        }
    } else {
        allPermissionsGranted()
    }
}







