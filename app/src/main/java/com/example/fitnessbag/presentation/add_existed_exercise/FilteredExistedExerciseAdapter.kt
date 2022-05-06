package com.example.fitnessbag.presentation.add_existed_exercise

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.databinding.ItemImageExerciseBinding
import com.example.fitnessbag.presentation.utils.loadImage
import com.example.fitnessbag.presentation.workout_detail.toDoneToString
import com.example.fitnessbag.presentation.workout_detail.toSecondsToDoneString
import com.example.fitnessbag.utils.removeWithAfter
import java.util.*
import kotlin.collections.ArrayList

class FilteredExistedExerciseAdapter(val exercises: List<ExerciseModel>, private val onClick: (item: ExerciseModel) -> Unit) :
    RecyclerView.Adapter<FilteredExistedExerciseAdapter.ExerciseViewHolder>() {

    var filteredExercises: MutableList<ExerciseModel> = ArrayList(exercises)
    var filterString = ""

    @SuppressLint("NotifyDataSetChanged")
    fun setTextFilter(filterString: String) {
        if (this.filterString != "" && startWithLower(filterString, this.filterString)) {
            filteredExercises.removeWithAfter({
                !startWithLower(it.name, filterString)
            }) { exerciseModel: ExerciseModel, i: Int -> notifyItemRemoved(i) }

            this.filterString = filterString
            
            return
        }
        
        filteredExercises = ArrayList(exercises.filter {
            startWithLower(it.name, filterString)
        })

        this.filterString = filterString
        notifyDataSetChanged()
    }

    private fun startWithLower(it: String, arg: String) =
        it.lowercase(Locale.getDefault())
            .startsWith(arg.lowercase(Locale.getDefault()))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExerciseViewHolder(ItemImageExerciseBinding.inflate(inflater, parent, false), onClick)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.setModel(filteredExercises[position])
    }

    override fun getItemCount(): Int {
        return filteredExercises.size
    }

    class ExerciseViewHolder(val binding: ItemImageExerciseBinding, onClick: (item: ExerciseModel) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private var model: ExerciseModel? = null

        init {
            binding.root.setOnClickListener { 
                onClick.invoke(model!!)
            }
        }
        
        fun setModel(model: ExerciseModel) {
            this.model = model
            binding.exerciseImageView.loadImage(model.image)
            binding.descriptionTextView.text = model.description
            binding.titleTextView.text = model.name
            binding.restSecondsTextView.text = model.restSeconds.toSecondsToDoneString()
            binding.toDoneTextView.text = model.toDoneToString()
        }
    }
}