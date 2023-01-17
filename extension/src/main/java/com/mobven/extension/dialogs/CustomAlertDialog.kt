package com.mobven.extension.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobven.extension.drawable

class CustomAlertDialog(
    val context: Context,
    @DimenRes val width: Int = 0,
    @DrawableRes val bg: Int = 0,
    private val viewHolderCreator: (dialog: CustomAlertDialog) -> View?,
) {

    private var builder: MaterialAlertDialogBuilder? = null
    var cancelable: Boolean = true
    var isBackGroundTransparent: Boolean = true

    var dialog: AlertDialog? = null

    fun create(): AlertDialog {
        viewHolderCreator(this)?.let {
            builder = MaterialAlertDialogBuilder(context).setView(it)
        }
        dialog = builder?.setCancelable(cancelable)?.create()

        if (isBackGroundTransparent) {
            context.apply {
                if (bg != 0) {
                    dialog?.window?.setBackgroundDrawable(
                        drawable(bg)
                    )
                }
                if (width == 0) {
                    dialog?.window?.setLayout(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                } else {
                    dialog?.window?.setLayout(
                        resources.getDimensionPixelSize(width),
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            }
        }

        return dialog!!
    }

    fun onCancelListener(func: () -> Unit): MaterialAlertDialogBuilder? =
        builder?.setOnCancelListener { func.invoke() }
}

fun customDialogOf(
    context: Context,
    @DimenRes width: Int = 0,
    @DrawableRes bg: Int = 0,
    viewHolderCreator: (dialog: CustomAlertDialog) -> View?,
) {
    CustomAlertDialog(context, width, bg, viewHolderCreator).create().show()
}

