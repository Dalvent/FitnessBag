package com.example.fitnessbag.presentation.add_existed_exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.repositories.exercise.ExerciseRepository

class AddExistedExerciseViewModel(val exerciseRepository: ExerciseRepository) : ViewModel() {
    val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> = _exercises
    
    init {
        _exercises.value = exerciseRepository.getAll()
    }
    
    fun remove(exercise: Exercise) {
        exerciseRepository.remove(exercise)
    }
}