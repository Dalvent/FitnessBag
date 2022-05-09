package com.example.fitnessbag.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.databinding.ItemTagBigBinding
import com.example.fitnessbag.databinding.ItemTagBinding

class TagsAdapter(val useBig: Boolean = false) : RecyclerView.Adapter<TagsAdapter.BaseTagViewHolder>() {

    private var tags: List<String> = listOf()

    fun updateItems(catalog: List<String>) {
        this.tags = catalog
        this.notifyItemRangeChanged(0, catalog.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseTagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if(useBig) {
            val view = ItemTagBigBinding.inflate(inflater)
            return TagViewHolderBig(view)
        }
        else {
            val view = ItemTagBinding.inflate(inflater)
            return TagViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseTagViewHolder, position: Int) {
        holder.setTitle(tags[position])
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    abstract class BaseTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun setTitle(title: String)
    }
    
    class TagViewHolder(val itemTagBinding: ItemTagBinding) : BaseTagViewHolder(itemTagBinding.root) {
        override fun setTitle(title: String) {
            itemTagBinding.titleTextView.text = title
        }
    }
    class TagViewHolderBig(val itemTagBinding: ItemTagBigBinding) : BaseTagViewHolder(itemTagBinding.root) {
        override fun setTitle(title: String) {
            itemTagBinding.titleTextView.text = title
        }
    }
}