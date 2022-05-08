package com.example.fitnessbag.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitnesbag.data.model.ExerciseEntity
import com.example.fitnesbag.data.model.WorkoutEntity
import com.example.fitnessbag.data.dao.ExerciseDao
import com.example.fitnessbag.data.dao.TagsDao
import com.example.fitnessbag.data.dao.WorkoutDao
import com.example.fitnessbag.data.entities.ExercisesInWorkoutEntity
import com.example.fitnessbag.data.entities.TagEntity
import com.example.fitnessbag.data.entities.TagsInWorkoutEntity

@Database(entities = [ExerciseEntity::class, ExercisesInWorkoutEntity::class, TagEntity::class, TagsInWorkoutEntity::class, WorkoutEntity::class], version = 1)
abstract class FitnessBagDatabase : RoomDatabase() {
    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getTagsDao(): TagsDao
    abstract fun getWorkoutDao(): WorkoutDao
}