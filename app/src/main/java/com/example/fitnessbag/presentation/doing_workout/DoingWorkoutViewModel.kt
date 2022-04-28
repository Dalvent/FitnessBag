package com.example.fitnessbag.presentation.doing_workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessbag.data.repositories.WorkoutDetailRepository
import com.example.fitnessbag.presentation.BaseViewModel

enum class DoingWorkoutState {
    Exercise,
    Rest
}

class DoingWorkoutViewModel(private val workoutDetailRepository: WorkoutDetailRepository) : BaseViewModel() {
    private val _doingWorkoutState = MutableLiveData<DoingWorkoutState>()
    val doingWorkoutState : LiveData<DoingWorkoutState> = _doingWorkoutState

    fun updateState(state: DoingWorkoutState) {
        _doingWorkoutState.value = state
    }
}