package com.example.fitnessbag.presentation.workout_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessbag.domain.models.DoneWorkout
import com.example.fitnessbag.domain.repositories.workout.WorkoutRepository

class WorkoutHistoryViewModel(val workoutRepository: WorkoutRepository) : ViewModel() {
    private val _downedWorkouts = MutableLiveData<List<DoneWorkout>>()
    val downedWorkouts: LiveData<List<DoneWorkout>> = _downedWorkouts
    
    init {
        _downedWorkouts.value = workoutRepository.getDonned().sortedBy { it.date }
    }
}