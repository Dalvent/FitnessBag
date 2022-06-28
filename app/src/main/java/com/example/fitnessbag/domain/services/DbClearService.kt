package com.example.fitnessbag.domain.services

import com.example.fitnessbag.data.FitnessBagDatabase

interface DbClearService {
    fun clearAll()
}

class DbClearServiceImpl(val database: FitnessBagDatabase) : DbClearService {
    override fun clearAll() {
        database.clearAllTables()
    }
}