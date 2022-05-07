package com.example.fitnessbag.presentation.workouts_catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnessbag.data.models.WorkoutInCatalogModel
import com.example.fitnessbag.data.repositories.WorkoutRepository
import com.example.fitnessbag.presentation.BaseViewModel
import kotlinx.coroutines.launch

class WorkoutCatalogViewModel(private val workoutRepository: WorkoutRepository
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<WorkoutInCatalogModel>>()
    val workouts: MutableLiveData<List<WorkoutInCatalogModel>> = _categories

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