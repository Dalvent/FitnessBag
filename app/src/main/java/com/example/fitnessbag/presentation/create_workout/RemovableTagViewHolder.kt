package com.example.fitnessbag.presentation.create_workout

import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.databinding.ItemRemovableTagBinding

class RemovableTagViewHolder(val itemTagBinding: ItemRemovableTagBinding, val onPressed: (sender: RemovableTagViewHolder) -> Unit) : RecyclerView.ViewHolder(itemTagBinding.root) {
    init {
        itemTagBinding.root.setOnClickListener {
            onPressed.invoke(this)
        }
    }

    fun updateText(text: String) {
        itemTagBinding.titleEditText.text = text
    }
}