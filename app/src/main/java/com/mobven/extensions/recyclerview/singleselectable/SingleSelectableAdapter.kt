package com.mobven.extensions.recyclerview.singleselectable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobven.extension.click
import com.mobven.extension.gone
import com.mobven.extension.show
import com.mobven.extensions.databinding.ItemSelectableBinding

class SingleSelectableAdapter(private val items: List<SelectableModel>): RecyclerView.Adapter<SingleSelectableAdapter.ViewHolder>() {

    /**
     * This is for initial selected position
     * -1 means no item selected
     * if you have default selected position change this value
     */
    var selectedPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSelectableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemSelectableBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SelectableModel) {
            binding.apply {
                cbMaterial.text = item.title

                if (selectedPos == -1) {
                    item.isSelected = false
                    imgSelectable.gone()
                } else {
                    item.isSelected = selectedPos == adapterPosition
                    cbMaterial.isChecked = selectedPos == adapterPosition
                    if (selectedPos == adapterPosition) {
                        imgSelectable.show()
                    } else {
                        imgSelectable.gone()
                    }

                }

                itemView.click {
                    setChecked(selectedPos)
                }

            }
        }

        private fun setChecked(previousSelectedPos: Int) {
            binding.apply {
                selectedPos = adapterPosition
                items[adapterPosition].isSelected = true
                items[adapterPosition].title = "SELECTED Model $adapterPosition"
                notifyItemChanged(adapterPosition)
                if (previousSelectedPos != -1 && previousSelectedPos != adapterPosition) {
                    items[previousSelectedPos].isSelected = false
                    items[previousSelectedPos].title = "Selectable Model $previousSelectedPos"
                    notifyItemChanged(previousSelectedPos)
                }
            }
        }

    }

}