package com.example.fitnessbag.presentation.create_workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.data.models.WorkoutDetailModel
import com.example.fitnessbag.data.repositories.WorkoutRepository

class CreateWorkoutViewModel(val workoutRepository: WorkoutRepository) : ViewModel() {
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

    var _exercises = MutableLiveData<MutableList<ExerciseModel>>(mutableListOf())
    val exercises: LiveData<MutableList<ExerciseModel>> = _exercises
    
    fun save(): WorkoutDetailModel {
        val model = WorkoutDetailModel(
            0,
            _name.value!!,
            _description.value!!,
            "",
            _tags.value!!,
            _exercises.value!!
        )
        workoutRepository.add(model)
        
        return model
    }
}