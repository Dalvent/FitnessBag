package com.example.fitnessbag.presentation.create_workout

import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.databinding.ItemAddTagBinding

class AddTagButtonViewHolder(val itemTagBinding: ItemAddTagBinding, val onPressed: () -> Unit) : RecyclerView.ViewHolder(itemTagBinding.root) {
    init {
        itemTagBinding.root.setOnClickListener {
            onPressed.invoke()
        }
    }
}