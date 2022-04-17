package com.example.fitnessbag.presentation.workouts_catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessbag.data.models.WorkoutInCatalogModel
import com.example.fitnessbag.data.repositories.WorkoutInCatalogRepository
import com.example.fitnessbag.presentation.BaseViewModel
import kotlinx.coroutines.launch

class WorkoutCatalogViewModel(private val workoutInCatalogRepository: WorkoutInCatalogRepository
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<WorkoutInCatalogModel>>()
    val workouts: MutableLiveData<List<WorkoutInCatalogModel>> = _categories

    init {
        viewModelScope.launch {
            startLoading()
            _categories.value = workoutInCatalogRepository.get().sortedByDescending { it.id }
            stopLoading()
        }
    }
}