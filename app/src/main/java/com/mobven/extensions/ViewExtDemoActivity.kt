package com.mobven.extensions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mobven.extension.click
import com.mobven.extension.dial
import com.mobven.extension.makeCall
import com.mobven.extension.toast
import com.mobven.extensions.databinding.ActivityViewExtDemoBinding
import com.mobven.extensions.onactivityresultapi.ChooseFromGalleryActivity

class ViewExtDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewExtDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewExtDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        testViewExtension()
    }

    private fun testViewExtension() {
        binding.apply {
            btnClickExt.click {
                makeCall("5542029938")
            }
            btnChooseFromGallery.click {
                startActivity(Intent(this@ViewExtDemoActivity, ChooseFromGalleryActivity::class.java))
            }
        }
    }
}