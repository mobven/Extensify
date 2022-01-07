package com.mobven.extensions.recyclerview.concatadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobven.extensions.databinding.ItemHorizontalFeedBinding

class HorizontalFeedAdapter:
    RecyclerView.Adapter<HorizontalFeedAdapter.ViewHolder>() {

    private val dataList = mutableListOf<String>()
    private val subAdapter = HorizontalItemAdapter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = ItemHorizontalFeedBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(dataList)
    }

    override fun getItemCount(): Int = 1

    fun setData(list: MutableList<String>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemHorizontalFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(list: MutableList<String>) {
            binding.apply {
                rvHorizontalList.adapter = subAdapter.apply {
                    setData(list)
                }
            }
        }
    }

}
