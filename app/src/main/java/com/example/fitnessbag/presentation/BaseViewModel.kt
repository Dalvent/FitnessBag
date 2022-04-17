package com.example.fitnessbag.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel() : ViewModel() {
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun startLoading() {
        _loading.value = true
    }
    
    fun stopLoading() {
        _loading.value = false
    }
}