package com.example.fitnessbag.presentation.create_workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessbag.domain.services.ImagePickerService
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.Workout
import com.example.fitnessbag.domain.models.getToDone
import com.example.fitnessbag.domain.repositories.workout.WorkoutRepository
import com.example.fitnessbag.utils.ValidationException

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

    var _pathUrl = MutableLiveData<String>("")
    val pathUrl: LiveData<String> get() = _pathUrl
    fun setUrl(value: String) {
        if(_pathUrl.value == value)
            return;
        
        _pathUrl.value = value
    }

    var _tags = MutableLiveData<MutableList<String>>(mutableListOf())
    val tags: LiveData<MutableList<String>> = _tags

    var _exercises = MutableLiveData<MutableList<Exercise>>(mutableListOf())
    val exercises: LiveData<MutableList<Exercise>> = _exercises
    
    fun loadTemplate(workoutId: Long) {
        val detail = workoutRepository.getDetailed(workoutId)
        _name.value = detail.name
        _description.value = detail.description
        _pathUrl.value = detail.image
        _tags.value = detail.tags.toMutableList()
        _exercises.value = detail.exercises.toMutableList()
    }
    
    fun save(): Workout {
        if(_name.value.isNullOrBlank())
            throw ValidationException("Fill name!")
        if(_exercises.value?.isEmpty() == true)
            throw ValidationException("Set exercises!")
        if(_exercises.value?.any { it.getToDone() <= 0 } == true)
            throw ValidationException("All exercise must have non zero condition or not negative!")
        
        return workoutRepository.createNew(
            _name.value!!,
            _description.value!!,
            _pathUrl.value!!,
            _tags.value!!,
            _exercises.value!!
        )
    }
}