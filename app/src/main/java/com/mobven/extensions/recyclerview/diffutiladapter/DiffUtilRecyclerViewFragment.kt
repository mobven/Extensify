package com.mobven.extensions.recyclerview.diffutiladapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobven.extension.click
import com.mobven.extension.toast
import com.mobven.extensions.databinding.FragmentSingleSelectableRvBinding

class DiffUtilRecyclerViewFragment : Fragment() {

    private var _binding: FragmentSingleSelectableRvBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyDiffUtilAdapter

    private val stars = listOf(
        "Aldebaran",
        "Rigel",
        "Canopus",
        "Bellatrix",
        "Polaris",
        "Regulus",
        "VY Canis Majoris",
        "UY Scuti",
        "Deneb",
        "Wezen",
        "Arcturus"
    )
    private val galaxies = listOf(
        "Circunus",
        "Milky Way",
        "Andromeda",
        "StarBust",
        "Sombrero",
        "Pinwheel",
        "Cartwheel",
        "Large Magellonic Cloud"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleSelectableRvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDiffutil.click {
            adapter.updateList(galaxies)
        }
        useDiffUtil()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun useDiffUtil() {
        adapter = MyDiffUtilAdapter(stars)
        binding.apply {
            rvSingleSelectable.adapter = adapter
            rvSingleSelectable.setHasFixedSize(true)

            adapter.itemClickListener = { _, name ->
                requireContext().toast(name)
            }
            //adapter.updateList(galaxies)
        }
    }

}