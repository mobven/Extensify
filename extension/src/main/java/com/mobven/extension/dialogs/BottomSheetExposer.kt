package com.mobven.extension.dialogs

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobven.extension.R
import com.mobven.extension.drawable
import com.mobven.extension.heightPixels

class BottomSheetExposer(
    private val isFullScreen: Boolean = true,
    private val heightMultiplier: Int = 50,
    private val viewHolderCreator: (inflater: LayoutInflater, sheet: BottomSheetExposer) -> View?
) : BottomSheetDialogFragment() {

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        bottomSheet?.let { frame ->
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(frame)
            frame.layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
        }
    }

    private fun skipCollapsed(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        bottomSheet?.let {
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(it)
            it.minimumHeight = requireActivity().heightPixels() * heightMultiplier / 100
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
        }
    }

    override fun onStart() {
        super.onStart()
        if (isFullScreen) {
            val sheetContainer = requireView().parent as ViewGroup
            sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewHolderCreator(inflater, this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        if (isFullScreen) {
            dialog.setOnShowListener {
                val bottomSheetDialog = it as BottomSheetDialog
                setupFullHeight(bottomSheetDialog)
            }
        } else {
            dialog.setOnShowListener {
                val bottomSheetDialog = it as BottomSheetDialog
                skipCollapsed(bottomSheetDialog)
            }
        }

        return dialog
    }
}

fun bottomSheetOf(
    fragmentManager: FragmentManager,
    isFullScreen: Boolean = true,
    heightMultiplier: Int = 50,
    viewHolderCreator: (inflater: LayoutInflater, sheet: BottomSheetExposer) -> View?
) {
    BottomSheetExposer(
        isFullScreen,
        heightMultiplier,
        viewHolderCreator
    ).show(fragmentManager, "BOTTOM_SHEET")
}