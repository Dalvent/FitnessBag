package com.example.fitnessbag.presentation.create_workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.IOnHideKeyboardListener
import com.example.fitnessbag.databinding.ItemAddTagBinding
import com.example.fitnessbag.databinding.ItemEditableTagBinding
import com.example.fitnessbag.databinding.ItemRemovableTagBinding


class EditableTagsAdapter(val tags: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), 
    IOnHideKeyboardListener {
    
    companion object {
        const val ADD_TYPE = 0
        const val EDIT_TYPE = 1
        const val REMOVABLE_TYPE = 2
    }

    private var recyclerView: RecyclerView? = null
    private var editableTagViewHolder: EditableTagViewHolder? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        
        this.recyclerView = recyclerView
    }
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ADD_TYPE -> {
                val view = ItemAddTagBinding.inflate(inflater)
                return AddTagButtonViewHolder(view) {
                    if(editableTagViewHolder!!.isActive) {
                        editableTagViewHolder!!.invokeComplete()
                    }
                    else {
                        editableTagViewHolder!!.show()
                    }
                }
            }
            EDIT_TYPE -> {
                val view = ItemEditableTagBinding.inflate(inflater)
                editableTagViewHolder = EditableTagViewHolder(
                    view,
                ) {
                    tags.add(it)
                    notifyItemChanged(tags.size - 1)
                    notifyItemInserted(tags.size)
                }
                return editableTagViewHolder!!
            }
            else -> {
                val view = ItemRemovableTagBinding.inflate(inflater)
                RemovableTagViewHolder(view) {
                    val itemIndex = it.adapterPosition - 1
                    tags.removeAt(itemIndex)
                    notifyItemRemoved(it.adapterPosition)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0)
            return ADD_TYPE
        if(position == tags.size + 1)
            return EDIT_TYPE
        
        return REMOVABLE_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RemovableTagViewHolder) {
            holder.updateText(tags[position - 1])
        }
    }

    override fun getItemCount(): Int {
        return tags.size + 2
    }

    override fun keyboardHide() {
        if(editableTagViewHolder!!.isActive) {
            editableTagViewHolder!!.invokeComplete()
            editableTagViewHolder!!.hide()
        }
    }
}