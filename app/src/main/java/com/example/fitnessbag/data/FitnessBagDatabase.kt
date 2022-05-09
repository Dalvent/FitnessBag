package com.example.fitnessbag.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitnesbag.data.model.ExerciseEntity
import com.example.fitnesbag.data.model.WorkoutEntity
import com.example.fitnessbag.data.dao.ExerciseDao
import com.example.fitnessbag.data.dao.WorkoutDao
import com.example.fitnessbag.data.entities.DoneWorkoutEntity
import com.example.fitnessbag.data.entities.ExercisesInWorkoutEntity

@Database(entities = [ExerciseEntity::class, ExercisesInWorkoutEntity::class, WorkoutEntity::class, DoneWorkoutEntity::class], version = 1)
abstract class FitnessBagDatabase : RoomDatabase() {
    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getWorkoutDao(): WorkoutDao
}
