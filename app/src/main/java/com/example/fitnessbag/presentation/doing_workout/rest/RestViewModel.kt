package com.example.fitnessbag.presentation.doing_workout.rest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnessbag.presentation.BaseViewModel
import com.example.fitnessbag.presentation.doing_workout.WorkoutNavigator
import com.example.fitnessbag.presentation.workout_detail.toSecondsToDoneString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RestViewModel(private val workoutNavigator: WorkoutNavigator, private var restSeconds: Int) : BaseViewModel() {
    private val _remainingTime = MutableLiveData<String>()
    val remainingTime : LiveData<String> = _remainingTime;
    
    fun addSeconds(seconds: Int) {
        setSeconds(restSeconds + seconds)
    }
    
    init {
        viewModelScope.launch {
            _remainingTime.value = restSeconds.toSecondsToDoneString()
            while (restSeconds > 0) {
                delay(1000)
                setSeconds(restSeconds - 1)
            }
            skip()
        }
    }

    private fun setSeconds(seconds: Int) = synchronized(this) {
        restSeconds = seconds
        _remainingTime.value = restSeconds.toSecondsToDoneString()
    }
    

    fun skip() {
        workoutNavigator.navigateNext()
        restSeconds = 0
    }
}