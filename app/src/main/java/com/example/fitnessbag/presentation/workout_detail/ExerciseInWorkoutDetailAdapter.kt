package com.example.fitnessbag.presentation.workout_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.HeaderExerciseBinding
import com.example.fitnessbag.databinding.ItemImageExerciseBinding
import com.example.fitnessbag.databinding.LayoutDeleteBinding
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.toDoneToString
import com.example.fitnessbag.presentation.create_workout.CreateWorkoutFragmentDirections
import com.example.fitnessbag.presentation.utils.loadImage
import com.google.android.material.bottomsheet.BottomSheetDialog

class ExerciseInWorkoutDetailAdapter() :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val HEADER = 1 
    }
    
    private var exercises: List<Exercise> = listOf()

    fun updateItems(exercises: List<Exercise>) {
        this.exercises = exercises
        this.notifyItemRangeChanged(0, exercises.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if(viewType == HEADER) {
            val view = HeaderExerciseBinding.inflate(inflater, parent, false)
            return HeaderViewHolder(view)
        }
        
        val view = ItemImageExerciseBinding.inflate(inflater, parent, false)
        return ItemImageExerciseViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0)
            return HEADER
        
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder !is ItemImageExerciseViewHolder)
            return

        val exercise = exercises[position - 1]
        holder.itemBinding.titleTextView.text = exercise.name
        holder.itemBinding.exerciseImageView.loadImage(exercise.image, R.drawable.no_image_exercise) 
        holder.itemBinding.toDoneTextView.text = exercise.toDoneToString()
        holder.itemBinding.restSecondsTextView.text = exercise.restSeconds.toString()
    }

    override fun getItemCount(): Int {
        return exercises.size + 1
    }

    class HeaderViewHolder(val itemBinding: HeaderExerciseBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    }

    class ItemImageExerciseViewHolder(val itemBinding: ItemImageExerciseBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    }
}