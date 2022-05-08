package com.example.fitnessbag.presentation.doing_workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.repositories.workout.WorkoutRepository
import com.example.fitnessbag.presentation.BaseViewModel
import java.util.*

enum class DoingWorkoutState {
    Exercise,
    Rest,
    End
}

class DoingWorkoutViewModel(private val workoutRepository: WorkoutRepository) : BaseViewModel(), WorkoutNavigator {
    private val _doingWorkoutState = MutableLiveData<DoingWorkoutState>()
    val doingWorkoutState : LiveData<DoingWorkoutState> = _doingWorkoutState

    private var exerciseQueue: List<Exercise> = listOf()
    private var currentExerciseNumber: Int = 0
    fun currentExercise() = exerciseQueue.getOrNull(currentExerciseNumber)
    fun nextExercise() = exerciseQueue.getOrNull(currentExerciseNumber + 1)
    
    
    fun initialize(exercises: Collection<Exercise>) {
        exerciseQueue = LinkedList(exercises)
        navigateNext()
    }
    
    override fun navigateNext() {
        if(_doingWorkoutState.value == DoingWorkoutState.Exercise) {
            currentExerciseNumber++
            if(currentExercise() == null) {
                _doingWorkoutState.value = DoingWorkoutState.End
                return
            }
        }
        
        when(_doingWorkoutState.value) {
            DoingWorkoutState.Exercise -> _doingWorkoutState.value = DoingWorkoutState.Rest
            DoingWorkoutState.Rest -> _doingWorkoutState.value = DoingWorkoutState.Exercise
            null -> _doingWorkoutState.value = DoingWorkoutState.Exercise
        }
    }
}

interface WorkoutNavigator {
    fun navigateNext()
}