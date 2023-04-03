package com.mobven.extension.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.mobven.extension.R
import com.mobven.extension.heightPixels

class BottomSheetExposer(
    private val heightMultiplier: Int = 50,
    private val dialogTheme: Int,
    private val viewHolderCreator: (inflater: LayoutInflater, sheet: BottomSheetExposer) -> View?
) : BottomSheetDialogFragment() {

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        bottomSheet?.let { frame ->
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(frame)
            frame.layoutParams.height = requireActivity().heightPixels() * heightMultiplier / 100
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
        }
    }

    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as ViewGroup
        sheetContainer.layoutParams.height = requireActivity().heightPixels() * heightMultiplier / 100

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewHolderCreator(inflater, this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), dialogTheme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }
        return dialog
    }
}

fun bottomSheetOf(
    fragmentManager: FragmentManager,
    heightMultiplier: Int = 50,
    dialogTheme: Int = android.R.style.Theme_Material_NoActionBar_TranslucentDecor,
    viewHolderCreator: (inflater: LayoutInflater, sheet: BottomSheetExposer) -> View?
) {
    BottomSheetExposer(
        heightMultiplier,
        dialogTheme,
        viewHolderCreator
    ).show(fragmentManager, "BOTTOM_SHEET")
}