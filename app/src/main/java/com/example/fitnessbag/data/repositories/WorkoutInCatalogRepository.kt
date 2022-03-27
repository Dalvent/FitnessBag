package com.example.fitnessbag.data.repositories

import com.example.fitnessbag.data.models.WorkoutInCatalogModel

interface WorkoutInCatalogRepository {
    suspend fun get() : List<WorkoutInCatalogModel>
}

