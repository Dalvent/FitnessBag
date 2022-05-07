package com.example.fitnessbag.presentation.create_workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.databinding.ItemAddExerciseBinding
import com.example.fitnessbag.databinding.ItemAddedExerciseBinding
import com.example.fitnessbag.presentation.workout_detail.toDoneToString

class AddExercisesAdapter(val exercises: MutableList<ExerciseModel>, val onAddClick: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val ADD_BUTTON_TYPE = 0
        const val EXERCISE_TYPE = 1
    }
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        if(viewType == ADD_BUTTON_TYPE) {
            val binding = ItemAddExerciseBinding.inflate(inflater, parent, false)
            return AddExerciseViewHolder(binding, onAddClick)
        }
        
        val binding = ItemAddedExerciseBinding.inflate(inflater, parent, false)
        return ExerciseViewHolder(binding) {
            val exerciseIndex = exercises.indexOf(it)
            exercises.removeAt(exerciseIndex)
            notifyItemRemoved(exerciseIndex + 1)
        }
    }
    
    fun addExercise(model: ExerciseModel) {
        exercises.add(model.copy())
        notifyItemChanged(exercises.size - 1)
        notifyItemInserted(exercises.size)
    }

    override fun getItemViewType(position: Int): Int {
        if(position == exercises.size)
            return ADD_BUTTON_TYPE
        
        return EXERCISE_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExerciseViewHolder) {
            holder.setModel(exercises[position])
        }
    }

    override fun getItemCount(): Int {
        return exercises.size + 1
    }
    
    class ExerciseViewHolder(val binding: ItemAddedExerciseBinding, val onRemoveClick: (model: ExerciseModel) -> Unit)  : RecyclerView.ViewHolder(binding.root) {
        fun setModel(model: ExerciseModel) {
            binding.titleTextView.text = model.name
            binding.countTextView.text = model.toDoneToString()
            binding.removeButton.setOnClickListener {
                onRemoveClick.invoke(model)
            }
        }
    }
    
    class AddExerciseViewHolder(binding: ItemAddExerciseBinding, onAddClick: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.addButton.setOnClickListener { 
                onAddClick.invoke()
            }
        }
    }
}