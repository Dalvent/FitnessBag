package com.example.fitnessbag.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.databinding.ItemTagBinding

class TagsAdapter : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    private var tags: List<String> = listOf()

    fun updateItems(catalog: List<String>) {
        this.tags = catalog
        this.notifyItemRangeChanged(0, catalog.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemTagBinding.inflate(inflater)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.setTitle(tags[position])
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    class TagViewHolder(val itemTagBinding: ItemTagBinding) : RecyclerView.ViewHolder(itemTagBinding.root) {
        fun setTitle(title: String) {
            itemTagBinding.titleTextView.text = title
        }
    }
}