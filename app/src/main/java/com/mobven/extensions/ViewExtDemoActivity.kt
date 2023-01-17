package com.mobven.extensions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobven.extension.click
import com.mobven.extension.dialogs.*
import com.mobven.extension.drawable
import com.mobven.extension.toast
import com.mobven.extensions.databinding.ActivityChooseFromGalleryBinding.inflate
import com.mobven.extensions.databinding.ActivityViewExtDemoBinding
import com.mobven.extensions.databinding.CustomDialogBinding
import com.mobven.extensions.databinding.DialogBottomSheetBinding
import com.mobven.extensions.databinding.DialogFullScreenBinding
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
                fullScreenDialogOf(
                    supportFragmentManager,
                    R.style.FullScreenDialog,
                    viewHolderCreator = { layoutInflater, dialog ->
                        val binding = DialogFullScreenBinding.inflate(layoutInflater)
                        binding.root
                    })
            }
            btnChooseFromGallery.click {
                startActivity(
                    Intent(
                        this@ViewExtDemoActivity,
                        ChooseFromGalleryActivity::class.java
                    )
                )
            }

            btnBottomSheet.click {
                bottomSheetOf(
                    supportFragmentManager,
                    true,
                    30,
                    viewHolderCreator = { layoutInflater, sheet ->
                        val binding = DialogBottomSheetBinding.inflate(layoutInflater)
                        binding.root
                    }
                )
            }

            btnAlertDialogExt.click {
                alert {
                    setTitle("Title")
                    setMessage("Message")
                    positiveButton("Positive Button") {

                    }
                    negativeButton("Negative Button") {

                    }
                }
            }

            btnCustomAlertDialogExt.click {
                customDialogOf(this@ViewExtDemoActivity, viewHolderCreator = { dialog ->
                    val binding = CustomDialogBinding.inflate(layoutInflater).apply {
                        btnPrimary.click {
                            toast("Primary Button Clicked.")
                        }
                    }
                    binding.root
                })
            }
        }
    }
}