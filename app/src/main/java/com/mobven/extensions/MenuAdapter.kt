package com.mobven.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobven.extension.click
import com.mobven.extensions.databinding.ItemMenuBinding

class MenuAdapter(private val menuList: List<String>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    var menuClick: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuList[position])
    }

    override fun getItemCount(): Int = menuList.size

    inner class ViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.apply {
                tvMenu.text = item
                root.click {
                    menuClick.invoke(item)
                }
            }
        }
    }

}