package com.mobven.extensions.flow

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class FlowFragment: Fragment() {
    private val viewModel by viewModels<FlowViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
    }
}