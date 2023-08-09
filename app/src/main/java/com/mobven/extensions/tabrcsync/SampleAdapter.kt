package com.mobven.extensions.tabrcsync

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobven.extensions.databinding.ItemCategoryViewBinding
import com.mobven.extensions.databinding.ItemProductViewBinding

class SampleAdapter(var itemList: ArrayList<ProductModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var CATEGORY = 1
    private var PRODUCT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CATEGORY) {
            val categoryView = LayoutInflater.from(parent.context)
            val binding = ItemCategoryViewBinding.inflate(categoryView, parent, false)
            CategoryHolder(binding)
        } else {
            val productView = LayoutInflater.from(parent.context)
            val binding = ItemProductViewBinding.inflate(productView, parent, false)
            ProductHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position].isCategory) {
            CATEGORY
        } else {
            PRODUCT
        }
    }

    inner class CategoryHolder(var bind: ItemCategoryViewBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(position: Int) {
            bind.tvCategoryName.text = itemList[position].name
        }
    }

    inner class ProductHolder(var bind: ItemProductViewBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(position: Int) {
            bind.tvId.text = itemList[position].productId
            bind.tvName.text = itemList[position].name
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryHolder) {
            holder.bind(position)
        } else {
            (holder as ProductHolder).bind(position)
        }
    }
}