package com.example.fitnessbag.presentation.add_existed_exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.data.repositories.ExerciseRepository

class AddExistedExerciseViewModel(val exerciseRepository: ExerciseRepository) : ViewModel() {
    val _exercises = MutableLiveData<List<ExerciseModel>>()
    val exercises: LiveData<List<ExerciseModel>> = _exercises
    
    init {
        _exercises.value = exerciseRepository.getExisted()
    }
}