package com.example.fitnessbag.presentation.create_workout

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.App
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.HeaderAddExerciseBinding
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.databinding.ItemAddedExerciseBinding
import com.example.fitnessbag.domain.models.RepeatExercise
import com.example.fitnessbag.domain.models.TimeExercise
import com.example.fitnessbag.domain.models.copy
import com.example.fitnessbag.presentation.utils.loadImage
import com.example.fitnessbag.presentation.workout_detail.toDoneToString

class AddExercisesAdapter(val exercises: MutableList<Exercise>, val onAddClick: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val ADD_BUTTON_TYPE = 0
        const val EXERCISE_TYPE = 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        if (viewType == ADD_BUTTON_TYPE) {
            val binding = HeaderAddExerciseBinding.inflate(inflater, parent, false)
            return AddExerciseViewHolder(binding, onAddClick)
        }

        val binding = ItemAddedExerciseBinding.inflate(inflater, parent, false)
        return ExerciseViewHolder(binding) {
            val exerciseIndex = exercises.indexOf(it)
            exercises.removeAt(exerciseIndex)
            notifyItemRemoved(exerciseIndex + 1)
        }
    }

    fun addExercise(model: Exercise) {
        exercises.add(model.copy())
        notifyItemChanged(exercises.size - 1)
        notifyItemInserted(exercises.size)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return ADD_BUTTON_TYPE

        return EXERCISE_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExerciseViewHolder) {
            holder.setModel(exercises[position - 1])
        }
    }

    override fun getItemCount(): Int {
        return exercises.size + 1
    }

    class ExerciseViewHolder(
        val binding: ItemAddedExerciseBinding,
        val onRemoveClick: (model: Exercise) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var model: Exercise? = null

        init {

            binding.restSecondsEditText.addTextChangedListener {
                if(it == null)
                    return@addTextChangedListener

                onRestSecondsChanged(it)
            }


            binding.toDoneEditText.addTextChangedListener {
                if(it == null)
                    return@addTextChangedListener
                
                onToDoneChanged(it)
            }
        }
        
        fun setModel(model: Exercise) {
            this.model = model
            binding.titleTextView.text = model.name
            binding.toDoneTextView.text = exerciseTypeName(model)
            binding.toDoneEditText.setText(exerciseToDone(model).toString())
            binding.restSecondsEditText.setText(model.restSeconds.toString())
            binding.exerciseImageView.loadImage(model.image, R.drawable.no_image_exercise)
            binding.removeButton.setOnClickListener {
                onRemoveClick.invoke(model)
            }
        }

        fun onRestSecondsChanged(value: Editable) {
            val valueString = value.toString()
            if (valueString.isNullOrBlank())
                return
            model!!.restSeconds = valueString.toInt()
        }

        fun onToDoneChanged(value: Editable) {
            val valueString = value.toString()
            if (valueString.isNullOrBlank())
                return

            val value = valueString.toInt()

            when (model) {
                is RepeatExercise -> (model as RepeatExercise).repeatTimes = value
                is TimeExercise -> (model as TimeExercise).secondsToDone = value
            }
        }

        private fun exerciseToDone(model: Exercise): Int {
            return when (model) {
                is RepeatExercise -> model.repeatTimes
                is TimeExercise -> model.secondsToDone
            }
        }

        private fun exerciseTypeName(model: Exercise): String {
            return when (model) {
                is RepeatExercise -> App.instance.getString(R.string.repeats)
                is TimeExercise -> App.instance.getString(R.string.seconds)
            }
        }
    }

    class AddExerciseViewHolder(binding: HeaderAddExerciseBinding, onAddClick: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.addButton.setOnClickListener {
                onAddClick.invoke()
            }
        }
    }
}