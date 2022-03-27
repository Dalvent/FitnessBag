package com.example.fitnessbag.presentation.workouts_catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.R
import com.example.fitnessbag.data.models.WorkoutInCatalogModel
import com.example.fitnessbag.databinding.ItemWorkoutInCatalotBinding
import com.example.fitnessbag.presentation.TagsAdapter
import com.example.fitnessbag.presentation.utils.loadImage
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager


class WorkoutsCatalogAdapter :
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
        return WorkoutInCatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutInCatalogViewHolder, position: Int) {
        holder.setModel(catalog[position])
    }

    override fun getItemCount(): Int {
        return catalog.size
    }

    class WorkoutInCatalogViewHolder(val itemTagBinding: ItemWorkoutInCatalotBinding) :
        RecyclerView.ViewHolder(itemTagBinding.root) {
        
        init {
            itemTagBinding.tagsRecyclerView.adapter = TagsAdapter()
            val context = itemTagBinding.root.context
            val flexboxLayoutManager = FlexboxLayoutManager(context)
            flexboxLayoutManager.flexDirection = FlexDirection.ROW
            itemTagBinding.tagsRecyclerView.layoutManager = flexboxLayoutManager


            val itemDecoration = FlexboxItemDecoration(context)
            itemDecoration.setOrientation(FlexboxItemDecoration.BOTH)
            itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.shape_transparent_w8_h4))
            itemTagBinding.tagsRecyclerView.addItemDecoration(itemDecoration)
        }

        fun setModel(model: WorkoutInCatalogModel) {
            
            itemTagBinding.titleTextView.text = model.name
            itemTagBinding.descriptionTextView.text = model.description
            itemTagBinding.imageView.loadImage(model.image)

            val tagsAdapter = itemTagBinding.tagsRecyclerView.adapter as TagsAdapter
            tagsAdapter.updateItems(model.tags)
        }
    }
}