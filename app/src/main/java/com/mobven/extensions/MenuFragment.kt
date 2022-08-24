package com.mobven.extensions

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobven.extension.requestPermissions
import com.mobven.extension.toast
import com.mobven.extensions.alertdialog.AlertDialogActivity
import com.mobven.extensions.compose.ComposePlaygroundActivity
import com.mobven.extensions.compose.layout.LayoutComposeActivity
import com.mobven.extensions.databinding.FragmentMenuBinding
import com.mobven.extensions.recyclerview.concatadapter.ConcatExampleActivity
import com.mobven.extensions.remindmethere.MapsActivity

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
                context.toast("${it.key} is ${it.value}", Toast.LENGTH_LONG)
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
            Menu.DIFF_UTIL_LIST -> findNavController().navigate(R.id.action_menuFragment_to_diffUtilRecyclerView)
            Menu.VIEW_EXT -> findNavController().navigate(R.id.action_menuFragment_to_viewExtDemoActivity)
            Menu.REQUEST_PERMISSIONS -> permissionChecker.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            Menu.COMPOSE_PLAYGROUND -> startActivity(Intent(requireContext(), ComposePlaygroundActivity::class.java))
            Menu.LAYOUT_COMPOSE -> startActivity(Intent(requireContext(), LayoutComposeActivity::class.java))
            Menu.CONCAT_ADAPTER -> startActivity(Intent(requireContext(), ConcatExampleActivity::class.java))
            Menu.GEOFENCE -> startActivity(Intent(requireContext(), MapsActivity::class.java))
            Menu.ALERT_DIALOG -> startActivity(Intent(requireContext(), AlertDialogActivity::class.java))
        }
    }

}