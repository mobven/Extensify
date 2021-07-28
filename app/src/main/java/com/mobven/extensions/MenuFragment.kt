package com.mobven.extensions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobven.extensions.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
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
            "Single Selectable RecyclerView" -> findNavController().navigate(R.id.action_menuFragment_to_singleSelectableRecyclerView)
        }
    }

}