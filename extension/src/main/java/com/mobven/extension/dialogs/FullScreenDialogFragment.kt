package com.mobven.extension.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class FullScreenDialogFragment(private val dialogTheme: Int, private val viewHolderCreator: (inflater: LayoutInflater, dialog: FullScreenDialogFragment) -> View?) :
    DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, dialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewHolderCreator(inflater, this)
    }
}

fun fullScreenDialogOf(
    fragmentManager: FragmentManager,
    dialogTheme: Int = android.R.style.Theme_Material_NoActionBar_TranslucentDecor,
    viewHolderCreator: (inflater: LayoutInflater, dialog: FullScreenDialogFragment) -> View?
) {
    FullScreenDialogFragment(dialogTheme, viewHolderCreator).show(fragmentManager, "FULL_SCREEN_DIALOG")
}