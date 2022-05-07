package com.example.fitnessbag.presentation.workout_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.data.models.WorkoutDetailModel
import com.example.fitnessbag.data.repositories.WorkoutRepository
import com.example.fitnessbag.presentation.BaseViewModel

class WorkoutDetailViewModel(private val workoutRepository: WorkoutRepository
) : BaseViewModel() {

    lateinit var workoutModel: WorkoutDetailModel
    
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _tags = MutableLiveData<List<String>>()
    val tags: LiveData<List<String>> = _tags

    private val _exercise = MutableLiveData<List<ExerciseModel>>()
    val exercise: LiveData<List<ExerciseModel>> = _exercise

    fun initialize(id: Int) {
        startLoading()
        workoutModel = workoutRepository.getDetailed(id)
        _name.value = workoutModel.name
        _description.value = workoutModel.description
        _tags.value = workoutModel.tags
        _exercise.value = workoutModel.exercises
        stopLoading()
    }
}