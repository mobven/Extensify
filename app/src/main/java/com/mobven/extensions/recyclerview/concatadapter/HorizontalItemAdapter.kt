package com.mobven.extensions.recyclerview.concatadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobven.extensions.databinding.ItemHorizontalSubRvBinding

class HorizontalItemAdapter :
    RecyclerView.Adapter<HorizontalItemAdapter.ViewHolder>() {

    private val dataList = mutableListOf<String>()

    var itemClick: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = ItemHorizontalSubRvBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(list: MutableList<String>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemHorizontalSubRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(title: String) {
            binding.tvTitle.text = title
            binding.tvTitle.setOnClickListener {
                itemClick.invoke(title)
            }
        }
    }

}
