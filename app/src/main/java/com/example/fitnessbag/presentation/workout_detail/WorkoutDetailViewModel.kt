package com.example.fitnessbag.presentation.workout_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.Workout
import com.example.fitnessbag.domain.models.WorkoutDetail
import com.example.fitnessbag.domain.repositories.workout.WorkoutRepository
import com.example.fitnessbag.presentation.BaseViewModel

class WorkoutDetailViewModel(private val workoutRepository: WorkoutRepository
) : BaseViewModel() {

    lateinit var workoutModel: WorkoutDetail
    
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _tags = MutableLiveData<List<String>>()
    val tags: LiveData<List<String>> = _tags

    private val _exercise = MutableLiveData<List<Exercise>>()
    val exercise: LiveData<List<Exercise>> = _exercise

    fun initialize(id: Long) {
        startLoading()
        workoutModel = workoutRepository.getDetailed(id)
        _name.value = workoutModel.name
        _imageUrl.value = workoutModel.image
        _description.value = workoutModel.description
        _tags.value = workoutModel.tags
        _exercise.value = workoutModel.exercises
        stopLoading()
    }
}