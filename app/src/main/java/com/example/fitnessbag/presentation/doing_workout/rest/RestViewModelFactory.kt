package com.example.fitnessbag.presentation.doing_workout.rest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnessbag.presentation.doing_workout.WorkoutNavigator

class RestViewModelFactory(private val workoutNavigator: WorkoutNavigator, private val restSeconds: Int) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestViewModel(workoutNavigator, restSeconds) as T
    }
}