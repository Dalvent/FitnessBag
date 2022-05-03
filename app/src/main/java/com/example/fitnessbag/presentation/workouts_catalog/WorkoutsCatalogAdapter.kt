package com.example.fitnessbag.presentation.workouts_catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.R
import com.example.fitnessbag.data.models.WorkoutInCatalogModel
import com.example.fitnessbag.databinding.ItemWorkoutInCatalotBinding
import com.example.fitnessbag.presentation.TagsAdapter
import com.example.fitnessbag.presentation.applyTagsStyle
import com.example.fitnessbag.presentation.utils.loadImage
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager


class WorkoutsCatalogAdapter(private var itemClick: ((model: WorkoutInCatalogModel) -> Unit)) :
    RecyclerView.Adapter<WorkoutsCatalogAdapter.WorkoutInCatalogViewHolder>() {

    private var catalog: List<WorkoutInCatalogModel> = listOf()
    
    fun updateItems(catalog: List<WorkoutInCatalogModel>) {
        this.catalog = catalog
        notifyDataSetChanged();
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutInCatalogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemWorkoutInCatalotBinding.inflate(inflater, parent, false)
        
        return WorkoutInCatalogViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: WorkoutInCatalogViewHolder, position: Int) {
        holder.setModel(catalog[position])
    }

    override fun getItemCount(): Int {
        return catalog.size
    }

    class WorkoutInCatalogViewHolder(
        private val itemTagBinding: ItemWorkoutInCatalotBinding, 
        private val itemClick: ((model: WorkoutInCatalogModel) -> Unit)) :
        RecyclerView.ViewHolder(itemTagBinding.root) {
        
        init {
            itemTagBinding.tagsRecyclerView.applyTagsStyle(itemTagBinding.root.context)
            itemTagBinding.tagsRecyclerView.adapter = TagsAdapter()
        }

        fun setModel(model: WorkoutInCatalogModel) {
            
            itemTagBinding.titleTextView.text = model.name
            itemTagBinding.descriptionTextView.text = model.description
            itemTagBinding.imageView.loadImage(model.image)
            itemTagBinding.root.setOnClickListener {
                itemClick.invoke(model)
            }

            val tagsAdapter = itemTagBinding.tagsRecyclerView.adapter as TagsAdapter
            tagsAdapter.updateItems(model.tags)
        }
    }
}