package com.example.fitnessbag.presentation.doing_workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.WorkoutDetail
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

    private var workoutDetail: WorkoutDetail? = null
    private var currentExerciseNumber: Int = 0
    fun currentExercise() = workoutDetail?.exercises?.getOrNull(currentExerciseNumber)
    fun nextExercise() = workoutDetail?.exercises?.getOrNull(currentExerciseNumber + 1)
    
    
    fun initialize(workoutDetail: WorkoutDetail) {
        this.workoutDetail = workoutDetail
        navigateNext()
    }
    
    override fun navigateNext() {
        if(_doingWorkoutState.value == DoingWorkoutState.Exercise) {
            currentExerciseNumber++
            if(currentExercise() == null) {
                addToDoneHistory()
                _doingWorkoutState.value = DoingWorkoutState.End
                return
            }
        }
        
        when(_doingWorkoutState.value) {
            DoingWorkoutState.Exercise -> {
                if(nextExercise()?.restSeconds == 0) {
                    _doingWorkoutState.value = DoingWorkoutState.Exercise
                }
                else {
                    _doingWorkoutState.value = DoingWorkoutState.Rest
                }
            } 
            DoingWorkoutState.Rest -> _doingWorkoutState.value = DoingWorkoutState.Exercise
            null -> _doingWorkoutState.value = DoingWorkoutState.Exercise
        }
    }

    private fun addToDoneHistory() {
        workoutRepository.applyDonned(workoutDetail!!, Date())
    }
}

interface WorkoutNavigator {
    fun navigateNext()
}