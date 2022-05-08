package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import androidx.lifecycle.viewModelScope
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.TimeExercise
import com.example.fitnessbag.presentation.doing_workout.WorkoutNavigator
import com.example.fitnessbag.presentation.workout_detail.toSecondsToDoneString
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimeDoingExerciseViewModel(private val workoutNavigator: WorkoutNavigator, private val exercise: TimeExercise
) : DoingExerciseViewModel() {
    
    private var timerJob: Job? = null
    private var remainingTime: Int = 0
    
    private fun isTimerStart() = timerJob != null
    
    init {
        remainingTime = exercise.secondsToDone
        _statusToDone.value = remainingTime.toSecondsToDoneString()
        _name.value = exercise.name
        _imageUrl.value = exercise.image

        startTimer()
    }

    private fun startTimer() {
        _specialButtonText.value = "Pause"
        timerJob = viewModelScope.launch {
            while (remainingTime > 0) {
                delay(1000)
                setSeconds(remainingTime - 1)
            }
            workoutNavigator.navigateNext()
        }
        
    }
    
    private fun stopTimer() {
        _specialButtonText.value = "Start"
        timerJob!!.cancel()
        timerJob = null
    }

    private fun setSeconds(seconds: Int) = synchronized(this) {
        remainingTime = seconds
        _statusToDone.value = remainingTime.toSecondsToDoneString()
    }
    
    override fun pressedSpecialButton() {
        if(isTimerStart()) {
            stopTimer()
        } 
        else {
            startTimer()
        }
    }
}