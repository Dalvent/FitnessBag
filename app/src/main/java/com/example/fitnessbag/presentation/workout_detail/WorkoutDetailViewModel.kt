package com.example.fitnessbag.presentation.workout_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessbag.data.models.WorkoutInCatalogModel
import com.example.fitnessbag.data.repositories.WorkoutInCatalogRepository
import kotlinx.coroutines.launch

/*
class WorkoutDetailViewModel(private val workoutInCatalogRepository: WorkoutInCatalogRepository
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _categories = MutableLiveData<List<WorkoutInCatalogModel>>()
    val workouts: MutableLiveData<List<WorkoutInCatalogModel>> = _categories

    init {
        viewModelScope.launch {
            _loading.value = true
            _categories.value = workoutInCatalogRepository.get().sortedByDescending { it.id }
            _loading.value = false
        }
    }
}*/