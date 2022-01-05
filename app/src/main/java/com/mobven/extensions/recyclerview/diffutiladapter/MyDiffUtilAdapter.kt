package com.mobven.extensions.recyclerview.diffutiladapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobven.extensions.databinding.ItemMenuBinding

/**
 * This is the same RecyclerView but using DiffUtil instead of
 * notifyDataSetChanged() to animate changes
 */
class MyDiffUtilAdapter(var galaxies: List<String>) :
    RecyclerView.Adapter<MyDiffUtilAdapter.ViewHolder>() {

    var itemClickListener: ((position: Int, name: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewHolderType: Int): ViewHolder {
        val binding =
            ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = galaxies.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(galaxies[position], position)
    }

    inner class ViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(galaxy: String, position: Int) {
            binding.apply {
                tvMenu.text = galaxy
                itemView.setOnClickListener { itemClickListener?.invoke(position, galaxy) }
            }
        }
    }

    /**
     *  THIS IS THE ONLY DIFFERENCE BETWEEN the regular MyNotifyDataSetAdapter
     */
    fun updateList(newGalaxies: List<String>) {
        val diffCallback = MyDiffCallback(galaxies, newGalaxies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)

        galaxies = newGalaxies
    }
}
//end