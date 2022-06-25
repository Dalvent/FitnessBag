package com.example.fitnessbag

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.fitnessbag.domain.services.AppInitService
import com.example.fitnessbag.domain.services.ImagePickerService

class MainViewModel(val imagePickerService: ImagePickerService, val appInitService: AppInitService) : ViewModel() {
    fun initIfNeeded(context: Context) {
        if(!appInitService.isInitialized(context))
            appInitService.initialize(context)
    }
}