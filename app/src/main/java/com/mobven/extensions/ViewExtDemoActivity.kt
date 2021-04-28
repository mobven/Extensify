package com.mobven.extensions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mobven.extension.click
import com.mobven.extension.toast
import com.mobven.extensions.databinding.ActivityViewExtDemoBinding

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
                toast("Clicked")
            }
        }
    }
}