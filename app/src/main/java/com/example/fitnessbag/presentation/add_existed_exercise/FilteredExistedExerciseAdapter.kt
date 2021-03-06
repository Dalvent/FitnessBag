package com.example.fitnessbag.presentation.add_existed_exercise

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.R
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.databinding.ItemImageExerciseBinding
import com.example.fitnessbag.databinding.LayoutDeleteBinding
import com.example.fitnessbag.domain.startWithLower
import com.example.fitnessbag.presentation.utils.loadImage
import com.example.fitnessbag.presentation.workout_detail.toDoneToString
import com.example.fitnessbag.presentation.workout_detail.toSecondsToDoneString
import com.example.fitnessbag.utils.removeWithAfter
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.internal.notify
import java.util.*
import kotlin.collections.ArrayList

class FilteredExistedExerciseAdapter(exercises: List<Exercise>, val addExistedExerciseViewModel: AddExistedExerciseViewModel, private val onClick: (item: Exercise) -> Unit) :
    RecyclerView.Adapter<FilteredExistedExerciseAdapter.ExerciseViewHolder>() {

    val exercises = exercises.toMutableList()
    var filteredExercises: MutableList<Exercise> = ArrayList(exercises)
    var filterString = ""

    @SuppressLint("NotifyDataSetChanged")
    fun setTextFilter(filterString: String) {
        if (this.filterString != "" || filterString.startWithLower(this.filterString)) {
            filteredExercises.removeWithAfter({
                !it.name.startWithLower(filterString)
            }) { exercise: Exercise, i: Int -> notifyItemRemoved(i) }

            this.filterString = filterString
            
            return
        }
        
        filteredExercises = ArrayList(exercises.filter {
            it.name.startWithLower(filterString)
        })

        this.filterString = filterString
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExerciseViewHolder(ItemImageExerciseBinding.inflate(inflater, parent, false), onClick)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val model = filteredExercises[position]
        holder.setModel(model)
        holder.binding.moreButton.visibility = View.VISIBLE
        holder.binding.moreButton.setOnClickListener {
            showBottomSheetDialog(model, holder.binding.root.context)
        }
    }

    override fun getItemCount(): Int {
        return filteredExercises.size
    }

    private fun showBottomSheetDialog(exercise: Exercise, context: Context) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val layoutDeleteBinding = LayoutDeleteBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialog.setContentView(layoutDeleteBinding.root)

        layoutDeleteBinding.deleteLinearLayout.setOnClickListener {
            addExistedExerciseViewModel.remove(exercise)
            
            val indexToUpdate = filteredExercises.indexOf(exercise);
            
            exercises.remove(exercise)
            filteredExercises.remove(exercise)
            notifyItemRemoved(indexToUpdate)
            bottomSheetDialog.hide()
        }
        bottomSheetDialog.show()
    }
    
    class ExerciseViewHolder(val binding: ItemImageExerciseBinding, onClick: (item: Exercise) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private var model: Exercise? = null

        init {
            binding.root.setOnClickListener { 
                onClick.invoke(model!!)
            }
        }
        
        fun setModel(model: Exercise) {
            this.model = model
            binding.exerciseImageView.loadImage(model.image, R.drawable.no_image_exercise)
            binding.titleTextView.text = model.name
            binding.restSecondsTextView.text = model.restSeconds.toSecondsToDoneString()
            binding.toDoneTextView.text = model.toDoneToString()
        }
    }
}