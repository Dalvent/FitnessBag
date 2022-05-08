package com.example.fitnessbag.presentation.workouts_catalog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.databinding.ItemBottomSpacerBinding
import com.example.fitnessbag.databinding.ItemWorkoutInCatalotBinding
import com.example.fitnessbag.databinding.LayoutWhatExerciseAddBinding
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.Workout
import com.example.fitnessbag.domain.startWithLower
import com.example.fitnessbag.presentation.TagsAdapter
import com.example.fitnessbag.presentation.applyTagsStyle
import com.example.fitnessbag.presentation.create_workout.CreateWorkoutFragmentDirections
import com.example.fitnessbag.presentation.utils.loadImage
import com.example.fitnessbag.utils.removeWithAfter
import com.google.android.material.bottomsheet.BottomSheetDialog


class WorkoutsCatalogAdapter(
    private var catalog: List<Workout>,
    private val itemClick: ((model: Workout) -> Unit),
    private val itemMoreClick: ((model: Workout) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val SPACER = 1
    }
    
    var filteredWorkouts: MutableList<Workout> = ArrayList(catalog)
    var filterString = ""

    @SuppressLint("NotifyDataSetChanged")
    fun setTextFilter(filterString: String) {
        if (this.filterString == "" || filterString.startWithLower(this.filterString)) {
            filteredWorkouts.removeWithAfter({
                !(isFitsFilter(it, filterString))
            }) { workout: Workout, i: Int -> notifyItemRemoved(i) }

            this.filterString = filterString

            return
        }

        filteredWorkouts = ArrayList(catalog.filter {
            isFitsFilter(it, filterString)
        })

        notifyDataSetChanged()
        this.filterString = filterString
    }

    fun removeWithViewModel(viewModel: WorkoutCatalogViewModel, workout: Workout) {
        viewModel.deleteWorkout(workout)
        catalog = viewModel.catalog.value!!
        val workoutIndex = filteredWorkouts.indexOf(workout)
        filteredWorkouts.remove(workout)
        notifyItemRemoved(workoutIndex)
    }
    
    private fun isFitsFilter(it: Workout, filterString: String): Boolean {
        return it.name.contains(filterString) || it.tags.any { it.contains(filterString) }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == filteredWorkouts.size)
            return SPACER
        return super.getItemViewType(position)
    }
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if(viewType == SPACER) {
            val bottomSpacer = ItemBottomSpacerBinding.inflate(inflater, parent, false);
            return SpacerViewHolder(bottomSpacer)
        }
        
        val view = ItemWorkoutInCatalotBinding.inflate(inflater, parent, false)
        
        return WorkoutInCatalogViewHolder(view, itemClick, itemMoreClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == filteredWorkouts.size)
            return
        
        (holder as WorkoutInCatalogViewHolder).setModel(filteredWorkouts[position])
    }

    override fun getItemCount(): Int {
        return filteredWorkouts.size + 1
    }

    class SpacerViewHolder(binding: ItemBottomSpacerBinding) : RecyclerView.ViewHolder(binding.root) {
        
    }
    
    class WorkoutInCatalogViewHolder(
        private val itemTagBinding: ItemWorkoutInCatalotBinding, 
        private val itemClick: ((model: Workout) -> Unit),
        private val itemMoreClick: ((model: Workout) -> Unit)) :
        RecyclerView.ViewHolder(itemTagBinding.root) {
        
        init {
            itemTagBinding.tagsRecyclerView.applyTagsStyle(itemTagBinding.root.context)
            itemTagBinding.tagsRecyclerView.adapter = TagsAdapter()
        }

        fun setModel(model: Workout) {
            itemTagBinding.titleTextView.text = model.name
            itemTagBinding.moreButton.setOnClickListener {
                itemMoreClick.invoke(model)
            }
            itemTagBinding.imageView.loadImage(model.image)
            itemTagBinding.root.setOnClickListener {
                itemClick.invoke(model)
            }

            val tagsAdapter = itemTagBinding.tagsRecyclerView.adapter as TagsAdapter
            tagsAdapter.updateItems(model.tags)
        }
    }
}