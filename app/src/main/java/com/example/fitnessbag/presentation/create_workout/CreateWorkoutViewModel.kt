package com.example.fitnessbag.presentation.create_workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessbag.domain.ImagePickerService
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.Workout
import com.example.fitnessbag.domain.repositories.workout.WorkoutRepository

class CreateWorkoutViewModel(val workoutRepository: WorkoutRepository, val imagePickerService: ImagePickerService) : ViewModel() {
    var _name = MutableLiveData<String>("")
    val name: LiveData<String> get() = _name
    fun setName(value: String) {
        _name.value = value
    }
    
    var _description = MutableLiveData<String>("")
    val description: LiveData<String> get() = _description
    fun setDescription(value: String) {
        _description.value = value
    }

    var _tags = MutableLiveData<MutableList<String>>(mutableListOf())
    val tags: LiveData<MutableList<String>> = _tags

    var _exercises = MutableLiveData<MutableList<Exercise>>(mutableListOf())
    val exercises: LiveData<MutableList<Exercise>> = _exercises
    
    fun save(): Workout {
        return workoutRepository.createNew(
            _name.value!!,
            _description.value!!,
            "",
            _tags.value!!,
            _exercises.value!!
        )
    }
}