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
    val catalog: MutableLiveData<List<Workout>> = _categories

    private val _filterString = MutableLiveData<String>()
    val filterString: MutableLiveData<String> = _filterString
    
    init {
        viewModelScope.launch {
            update()
        }
    }
    
    fun updateFilterString(filterString: String) {
        if(_filterString.value == filterString)
            return
        
        _filterString.value = filterString
    }

    fun deleteWorkout(workout: Workout) {
        workoutRepository.delete(workout)
        (_categories.value as MutableList<Workout>).remove(workout)
    }
    
    fun update() {
        startLoading()
        _categories.value = workoutRepository.getForCatalog().sortedByDescending { it.name }.toMutableList()
        stopLoading()
    }
}