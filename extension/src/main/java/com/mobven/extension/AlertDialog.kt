package com.mobven.extension

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.DeprecationLevel.HIDDEN

@OptIn(ExperimentalContracts::class)
inline fun Context.alertDialog(
    title: CharSequence? = null,
    message: CharSequence? = null,
    @DrawableRes iconResource: Int = 0,
    isCancellable: Boolean = true,
    dialogConfig: AlertDialog.Builder.() -> Unit = {}
): AlertDialog {
    contract { callsInPlace(dialogConfig, InvocationKind.EXACTLY_ONCE) }
    return alertDialog(
        title = title,
        message = message,
        icon = null,
        isCancellable = isCancellable,
    ) {
        setIcon(iconResource)
        dialogConfig()
    }
}

@OptIn(ExperimentalContracts::class)
inline fun Context.alertDialog(
    title: CharSequence? = null,
    message: CharSequence? = null,
    icon: Drawable?,
    isCancellable: Boolean = true,
    dialogConfig: AlertDialog.Builder.() -> Unit = {}
): AlertDialog {
    contract { callsInPlace(dialogConfig, InvocationKind.EXACTLY_ONCE) }
    return AlertDialog.Builder(this).apply {
        this.title = title
        this.message = message
        setIcon(icon)
        setCancelable(isCancellable)
        dialogConfig()
    }.create()
}

inline fun AlertDialog.onShow(crossinline onShowListener: AlertDialog.() -> Unit) = apply {
    setOnShowListener { onShowListener() }
}

inline fun AlertDialog.Builder.onDismiss(crossinline handler: (dialog: DialogInterface) -> Unit) {
    setOnDismissListener { handler(it) }
}

inline val AlertDialog.positiveButton: Button
    get() = requireNotNull(getButton(AlertDialog.BUTTON_POSITIVE)) {
        "The dialog has no positive button or has not been shown yet."
    }

inline val AlertDialog.neutralButton: Button
    get() = requireNotNull(getButton(AlertDialog.BUTTON_NEUTRAL)) {
        "The dialog has no neutral button or has not been shown yet."
    }

inline val AlertDialog.negativeButton: Button
    get() = requireNotNull(getButton(AlertDialog.BUTTON_NEGATIVE)) {
        "The dialog has no negative button or has not been shown yet."
    }

var AlertDialog.Builder.titleResource: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@StringRes value) {
        setTitle(value)
    }

var AlertDialog.Builder.title: CharSequence?
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) {
        setTitle(value)
    }

var AlertDialog.Builder.messageResource: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@StringRes value) {
        setMessage(value)
    }

var AlertDialog.Builder.message: CharSequence?
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) {
        setMessage(value)
    }

inline fun AlertDialog.Builder.okButton(crossinline handler: (dialog: DialogInterface) -> Unit) {
    setPositiveButton(android.R.string.ok) { dialog: DialogInterface, _: Int -> handler(dialog) }
}

@Suppress("NOTHING_TO_INLINE")
inline fun AlertDialog.Builder.okButton() {
    setPositiveButton(android.R.string.ok, null)
}

inline fun AlertDialog.Builder.cancelButton(crossinline handler: (dialog: DialogInterface) -> Unit) {
    setNegativeButton(android.R.string.cancel) { dialog: DialogInterface, _: Int -> handler(dialog) }
}

@Suppress("NOTHING_TO_INLINE")
inline fun AlertDialog.Builder.cancelButton() {
    setNegativeButton(android.R.string.cancel, null)
}

inline fun AlertDialog.Builder.positiveButton(
    @StringRes textResId: Int,
    crossinline handler: (dialog: DialogInterface) -> Unit
) {
    setPositiveButton(textResId) { dialog: DialogInterface, _: Int -> handler(dialog) }
}

inline fun AlertDialog.Builder.neutralButton(
    @StringRes textResId: Int,
    crossinline handler: (dialog: DialogInterface) -> Unit
) {
    setNeutralButton(textResId) { dialog: DialogInterface, _: Int -> handler(dialog) }
}

inline fun AlertDialog.Builder.negativeButton(
    @StringRes textResId: Int,
    crossinline handler: (dialog: DialogInterface) -> Unit
) {
    setNegativeButton(textResId) { dialog: DialogInterface, _: Int -> handler(dialog) }
}

@PublishedApi
internal const val NO_GETTER = "Property does not have a getter"

@PublishedApi
internal inline val noGetter: Nothing
    get() = throw UnsupportedOperationException(NO_GETTER)