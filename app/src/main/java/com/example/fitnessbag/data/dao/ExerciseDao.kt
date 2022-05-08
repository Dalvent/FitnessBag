package com.example.fitnessbag.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fitnesbag.data.model.ExerciseEntity

@Dao
interface ExerciseDao {
    @Query(
        "SELECT exercise.id, exercise.name, exercise.description, exercise.image, exercise.conditionsType, exercise.repeatTimes, exercise.secondsToDone, exercise.restSeconds " +
                "FROM ExerciseEntity AS exercise " +
                "INNER JOIN ExercisesInWorkoutEntity AS inWorkout ON exercise.id == inWorkout.exerciseId " +
                "WHERE id == :workoutId;"
    )
    fun getFor(workoutId: Long): List<ExerciseEntity>

    @Query("SELECT * FROM ExerciseEntity")
    fun getAll(): List<ExerciseEntity>
    
    @Insert
    fun insert(exercise: ExerciseEntity): Long
}

