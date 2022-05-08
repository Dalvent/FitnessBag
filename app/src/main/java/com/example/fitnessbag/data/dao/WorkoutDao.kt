package com.example.fitnessbag.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnesbag.data.model.WorkoutEntity

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM WorkoutEntity")
    fun getAll(): List<WorkoutEntity>
    
    @Query("SELECT * FROM WorkoutEntity WHERE id == :workoutId")
    fun get(workoutId: Long): WorkoutEntity
    
    @Insert
    fun insert(workoutEntity: WorkoutEntity): Long
}