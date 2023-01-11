package com.mobven.extensions.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobven.extension.makeCall
import com.mobven.extensions.R

class ExtensionTestFragment: Fragment(R.layout.fragment_text) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireContext().makeCall("5542029938")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}