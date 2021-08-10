package com.mobven.extensions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobven.extension.requestPermissions
import com.mobven.extensions.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var permissionChecker: ActivityResultLauncher<Array<String>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionChecker = requireActivity().requestPermissions { permissions ->
            permissions?.entries?.forEach {
                Log.d("Permissions","${it.key} is ${it.value}")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
    }

    private fun initMenu() {
        binding.rvMenu.adapter =
            MenuAdapter(resources.getStringArray(R.array.menu_array).toMutableList()).apply {
                menuClick = { navigateToMenu(it) }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToMenu(menuStr: String) {
        when(menuStr) {
            Menu.SINGLE_SELECT_LIST -> findNavController().navigate(R.id.action_menuFragment_to_singleSelectableRecyclerView)
            Menu.VIEW_EXT -> findNavController().navigate(R.id.action_menuFragment_to_viewExtDemoActivity)
            Menu.REQUEST_PERMISSIONS -> permissionChecker.launch(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }

}