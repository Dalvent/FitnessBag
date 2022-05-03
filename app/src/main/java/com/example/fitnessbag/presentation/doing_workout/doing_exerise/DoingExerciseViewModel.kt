package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class DoingExerciseViewModel() : ViewModel() {
    protected val _statusToDone = MutableLiveData<String>()
    val statusToDone: LiveData<String> = _statusToDone
    
    protected val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    
    protected val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl

    protected val _specialButtonText = MutableLiveData<String>()
    val specialButtonText: LiveData<String> = _specialButtonText

    abstract fun pressedSpecialButton();
}