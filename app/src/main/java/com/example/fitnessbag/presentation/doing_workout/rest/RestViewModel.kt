package com.example.fitnessbag.presentation.doing_workout.rest

import androidx.lifecycle.*
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.repositories.exercise.ExerciseRepository
import com.example.fitnessbag.presentation.BaseViewModel
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutProgress
import com.example.fitnessbag.presentation.workout_detail.toSecondsToDoneString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RestViewModel(var exerciseRepository: ExerciseRepository) : BaseViewModel() {
    private lateinit var progress : DoingWorkoutProgress
    private var restSeconds : Int = 0

    private val _currentExerciseNumber = MutableLiveData<Int>()
    val currentExerciseNumber: LiveData<Int> = _currentExerciseNumber

    private val _exercisesCount = MutableLiveData<Int>()
    val exercisesCount: LiveData<Int> = _exercisesCount
    
    private val _remainingTime = MutableLiveData<String>()
    val remainingTime : LiveData<String> = _remainingTime;

    private val _isTimerExpired = MutableLiveData<Boolean>()
    val isTimerExpired : LiveData<Boolean> = _isTimerExpired
    
    private val _exercise = MutableLiveData<Exercise>()
    val nextExercise : LiveData<Exercise> = _exercise
    
    fun addSeconds(seconds: Int) {
        setSeconds(restSeconds + seconds)
    }
    
    fun initialize(progress: DoingWorkoutProgress, restSeconds: Int) {
        this.progress = progress
        this.restSeconds = restSeconds
        _currentExerciseNumber.value = progress.currentExerciseNumber
        _exercisesCount.value = progress.exercisesCount
        
        viewModelScope.launch {
            _remainingTime.value = restSeconds.toSecondsToDoneString()
            while (this@RestViewModel.restSeconds > 0) {
                delay(1000)
                setSeconds(this@RestViewModel.restSeconds - 1)
            }
            _isTimerExpired.value = true
        }
        _exercise.value = exerciseRepository.getInWorkout(progress.workoutId, progress.currentExerciseNumber)
    }
    
    private fun setSeconds(seconds: Int) = synchronized(this) {
        this.restSeconds = seconds
        _remainingTime.value = restSeconds.toSecondsToDoneString()
    }
    

    fun skip() {
        _isTimerExpired.value = true
        restSeconds = 0
    }
}