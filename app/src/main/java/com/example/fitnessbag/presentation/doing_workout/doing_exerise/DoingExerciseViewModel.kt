package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessbag.domain.models.*
import com.example.fitnessbag.domain.repositories.exercise.ExerciseRepository
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutProgress

enum class ExerciseProcessStatus {
    Doing,
    Donned,
    Skipped,
    GoPrevious
}

class DoingExerciseViewModel(val exerciseRepository: ExerciseRepository) : ViewModel() {
    private var _exercise: Exercise? = null
    val exercise: Exercise get() = _exercise!!
    
    private var _progress: DoingWorkoutProgress? = null
    val progress: DoingWorkoutProgress get() = _progress!!
    
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    
    private val _canGoPrevious = MutableLiveData<Boolean>()
    val canGoPrevious: LiveData<Boolean> = _canGoPrevious
    
    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl

    private val _currentExerciseNumber = MutableLiveData<Int>()
    val currentExerciseNumber: LiveData<Int> = _currentExerciseNumber

    private val _exercisesCount = MutableLiveData<Int>()
    val exercisesCount: LiveData<Int> = _exercisesCount
    
    val statusToDone = MutableLiveData<String>()
    
    val specialButtonDrawable: MutableLiveData<Int> = MutableLiveData<Int>()
    
    private val _exerciseProcessStatus: MutableLiveData<ExerciseProcessStatus> = MutableLiveData<ExerciseProcessStatus>(ExerciseProcessStatus.Doing)
    val exerciseProcessStatus: LiveData<ExerciseProcessStatus> = _exerciseProcessStatus
    
    var strategy: DoingExerciseStrategy? = null
    
    fun pressedSpecialButton() {
        strategy!!.pressedSpecialButton()
    }
    
    fun initialize(progress: DoingWorkoutProgress) {
        _progress = progress
        
        _exercise = exerciseRepository.getInWorkout(progress.workoutId, progress.currentExerciseNumber)
        _name.value = exercise.name
        _imageUrl.value = exercise.image

        _exercisesCount.value = progress.exercisesCount
        _currentExerciseNumber.value = progress.currentExerciseNumber
        
        _canGoPrevious.value = progress.currentExerciseNumber > 1 
        
        strategy = createStrategyFor(exercise)
    }
    
    fun markDonned() {
        _exerciseProcessStatus.value = ExerciseProcessStatus.Donned;
    }
    
    fun skip() {
        progress.skipExercise()
        _exerciseProcessStatus.value = ExerciseProcessStatus.Skipped;
    }
    
    fun goPrevious() {
        if(canGoPrevious.value != true)
            return
        
        progress.previousExercise()
        _exerciseProcessStatus.value = ExerciseProcessStatus.GoPrevious;
    }
    
    private fun createStrategyFor(exercise: Exercise) : DoingExerciseStrategy {
        when(exercise) {
            is RepeatExercise -> {
                return RepeatDoingExerciseStrategy(
                    exercise,
                    progress,
                    this
                )
            }
            is TimeExercise -> {
                return TimerDoingExerciseStrategy(
                    viewModelScope,
                    exercise,
                    progress,
                    this
                )
            }
        }
    }
}