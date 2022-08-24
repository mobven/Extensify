package com.mobven.extensions.alertdialog

import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.mobven.extension.*
import com.mobven.extensions.R
import com.mobven.extensions.databinding.ActivityAlertDialogBinding

class AlertDialogActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AlertDialogActivity"
    }

    private lateinit var binding: ActivityAlertDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sampleAlert1.setOnClickListener {
            alertTextOrCancel()
        }

        binding.sampleAlert2.setOnClickListener {
            alertTextOrImage()
        }

    }

    private fun alertTextOrCancel() {
        alertDialog {
            messageResource = R.string.sample_alert_message
            okButton { Log.d(TAG, "Sample Ok message clicked") }
            cancelButton() // cancel button optional
        }.onShow {
            positiveButton.setTextColor(R.color.colorAccent) // Positive Button color
        }.show()
    }

    private fun alertTextOrImage() {
        alertDialog {
            title = "Sample Title"
            message = "Sample Message"
            setIcon(R.drawable.common_google_signin_btn_icon_dark)
            cancelButton()
            negativeButton(R.string.no) {
                Log.d(TAG, "Negative button handler")
            }
        }.onShow {
            positiveButton.setTextColor(R.color.design_default_color_background) // Positive Button color
        }.show()
    }

}