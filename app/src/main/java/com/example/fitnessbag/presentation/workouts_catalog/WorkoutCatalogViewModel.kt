package com.example.fitnessbag.presentation.workouts_catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnessbag.domain.models.Workout
import com.example.fitnessbag.domain.repositories.workout.WorkoutRepository
import com.example.fitnessbag.presentation.BaseViewModel
import kotlinx.coroutines.launch

class WorkoutCatalogViewModel(private val workoutRepository: WorkoutRepository
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<Workout>>()
    val workouts: MutableLiveData<List<Workout>> = _categories

    init {
        viewModelScope.launch {
            update()
        }
    }

    fun update() {
        startLoading()
        _categories.value = workoutRepository.getForCatalog().sortedByDescending { it.id }
        stopLoading()
    }
}