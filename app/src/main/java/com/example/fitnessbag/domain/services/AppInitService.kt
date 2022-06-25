package com.example.fitnessbag.domain.services

import android.content.Context

interface AppInitService {
    fun isInitialized(context: Context): Boolean
    fun initialize(context: Context)
}

class AppInitServiceImpl(val defaultInsertDataService: DefaultInsertDataService) : AppInitService {
    companion object {
        const val APP_PREFERENCE = "APP_PREFERENCE"
        const val APP_PREFERENCE_IS_APP_INIT = "APP_PREFERENCE"
    }
    
    
    override fun isInitialized(context: Context): Boolean {
        return context
            .getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            .getBoolean(APP_PREFERENCE_IS_APP_INIT, false)
    }

    override fun initialize(context: Context) {
        setAppIsInit(context)
        
        defaultInsertDataService.insertDefault()
    }

    private fun setAppIsInit(context: Context) {
        val editor = context
            .getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            .edit()
        editor.putBoolean(APP_PREFERENCE_IS_APP_INIT, true)
        editor.apply()
    }
}