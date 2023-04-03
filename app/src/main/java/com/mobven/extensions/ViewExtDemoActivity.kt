package com.mobven.extensions

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.IBinder
import android.os.IInterface
import android.util.Log
import android.view.InputEvent
import androidx.appcompat.app.AppCompatActivity
import com.mobven.extension.click
import com.mobven.extension.dialogs.*
import com.mobven.extension.orFalse
import com.mobven.extensions.databinding.ActivityViewExtDemoBinding
import com.mobven.extensions.databinding.DialogBottomSheetBinding
import com.mobven.extensions.databinding.DialogFullScreenBinding
import com.mobven.extensions.onactivityresultapi.ChooseFromGalleryActivity
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class ViewExtDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewExtDemoBinding

    companion object {
        private var inputManager: InputManager? = null
        private var GET_SERVICE_METHOD: Method? = Class.forName("android.os.ServiceManager")
            .getDeclaredMethod("getService", String::class.java)
    }

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
                        dialog.dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
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
                    false,
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
                /*customDialogOf(this@ViewExtDemoActivity, viewHolderCreator = { dialog ->
                    val binding = CustomDialogBinding.inflate(layoutInflater).apply {
                        btnPrimary.click {
                            toast("Primary Button Clicked.")
                        }
                    }
                    binding.root
                })*/
                /*val pM = PowerManager(
                    getService(
                        "power",
                        "android.os.IPowerManager"
                    )
                )
                Log.i("SCRCPY", pM.isScreenOn.toString())*/
                //Log.i("SCRCPY", getInputManager())
            }
        }
    }

    private fun getService(service: String, type: String): IInterface? {
        return try {
            val binder = GET_SERVICE_METHOD?.invoke(
                null,
                service
            ) as IBinder
            val asInterfaceMethod = Class.forName("$type\$Stub").getMethod(
                "asInterface",
                IBinder::class.java
            )
            asInterfaceMethod.invoke(null, binder) as IInterface
        } catch (e: Exception) {
            throw AssertionError(e)
        }
    }

    fun getInputManager(): InputManager? {
        if (inputManager == null) {
            try {
                val getInstanceMethod =
                    android.hardware.input.InputManager::class.java.getDeclaredMethod("getInstance")
                val im = getInstanceMethod.invoke(null) as android.hardware.input.InputManager
                inputManager = InputManager(im)
            } catch (e: NoSuchMethodException) {
                throw java.lang.AssertionError(e)
            } catch (e: IllegalAccessException) {
                throw java.lang.AssertionError(e)
            } catch (e: InvocationTargetException) {
                throw java.lang.AssertionError(e)
            }
        }
        return inputManager
    }

    fun injectEvent(inputEvent: InputEvent?, displayId: Int, injectMode: Int): Boolean {
        return getInputManager()?.injectInputEvent(inputEvent, injectMode).orFalse()
    }

}