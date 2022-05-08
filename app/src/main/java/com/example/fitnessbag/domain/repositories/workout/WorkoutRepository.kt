package com.example.fitnessbag.domain.repositories.workout

import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.Workout
import com.example.fitnessbag.domain.models.WorkoutDetail

interface WorkoutRepository {
    fun getDetailed(id: Long): WorkoutDetail
    fun getForCatalog(): List<Workout>
    fun createNew(name: String, description: String, image: String, tags: List<String>, exercises: List<Exercise>): WorkoutDetail
    fun delete(workout: Workout)
}

