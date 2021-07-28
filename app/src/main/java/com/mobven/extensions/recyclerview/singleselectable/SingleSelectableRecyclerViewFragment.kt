package com.mobven.extensions.recyclerview.singleselectable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.mobven.extensions.databinding.FragmentSingleSelectableRvBinding

class SingleSelectableRecyclerViewFragment : Fragment() {

    private var _binding: FragmentSingleSelectableRvBinding? = null
    private val binding get() = _binding!!

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
        initData()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initData() {
        val data = mutableListOf<SelectableModel>()
        repeat((0..10).count()) {
            data.add(SelectableModel("Selectable Model $it"))
        }
        binding.rvSingleSelectable.apply {
            adapter = SingleSelectableAdapter(data)
            addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))
        }
    }

}