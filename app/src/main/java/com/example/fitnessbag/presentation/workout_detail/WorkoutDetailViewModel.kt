package com.example.fitnessbag.presentation.workout_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessbag.data.models.WorkoutDetailModel
import com.example.fitnessbag.data.models.WorkoutInCatalogModel
import com.example.fitnessbag.data.repositories.WorkoutDetailRepository
import com.example.fitnessbag.data.repositories.WorkoutInCatalogRepository
import com.example.fitnessbag.presentation.BaseViewModel
import kotlinx.coroutines.launch

class WorkoutDetailViewModel(private val workoutDetailRepository: WorkoutDetailRepository
) : BaseViewModel() {
    
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _tags = MutableLiveData<List<String>>()
    val tags: LiveData<List<String>> = _tags

    fun initialize(id: Int) {
        startLoading()
        val workoutModel = workoutDetailRepository.getWorkoutDetail(id)
        _name.value = workoutModel.name
        _description.value = workoutModel.description
        _tags.value = workoutModel.tags
        stopLoading()
    }
}