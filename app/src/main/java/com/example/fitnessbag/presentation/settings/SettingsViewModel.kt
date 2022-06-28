package com.example.fitnessbag.presentation.settings

import com.example.fitnessbag.domain.services.DbClearService
import com.example.fitnessbag.presentation.BaseViewModel

class SettingsViewModel(val clearService: DbClearService) : BaseViewModel() {
    fun clearData() {
        clearService.clearAll()
    }
}