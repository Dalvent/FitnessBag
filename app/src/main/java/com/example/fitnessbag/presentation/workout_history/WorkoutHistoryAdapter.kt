package com.example.fitnessbag.presentation.workout_history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.ItemHistoryWorkoutBinding
import com.example.fitnessbag.domain.models.DoneWorkout
import com.example.fitnessbag.presentation.TagsAdapter
import com.example.fitnessbag.presentation.applyTagsStyle
import com.example.fitnessbag.presentation.utils.loadImage
import java.text.SimpleDateFormat


class WorkoutHistoryAdapter(val items: List<DoneWorkout>) : RecyclerView.Adapter<WorkoutHistoryAdapter.WorkoutHistoryViewHolder>() {
    
    companion object {
        @SuppressLint("SimpleDateFormat")
        val format = SimpleDateFormat("HH:mm dd.MM.yyyy")
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        
        return WorkoutHistoryViewHolder(
            ItemHistoryWorkoutBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WorkoutHistoryViewHolder, position: Int) {
        holder.setModel(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class WorkoutHistoryViewHolder(val binding: ItemHistoryWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val tagsAdapter: TagsAdapter

        init {
            binding.tagsRecyclerView.applyTagsStyle(binding.root.context)
            tagsAdapter = TagsAdapter()
            binding.tagsRecyclerView.adapter = tagsAdapter
        }    
        fun setModel(doneWorkout: DoneWorkout) {
            binding.titleTextView.text = doneWorkout.name
            binding.exerciseImageView.loadImage(doneWorkout.image, R.drawable.no_image_workout)

            tagsAdapter.updateItems(doneWorkout.tags)
            
            binding.dateTextView.text = format.format(doneWorkout.date)
        }
    }
}