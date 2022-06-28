package com.example.fitnessbag.data.dao

import androidx.room.*
import com.example.fitnesbag.data.model.ExerciseEntity
import com.example.fitnessbag.data.entities.DoneWorkoutEntity
import com.example.fitnessbag.data.entities.ExercisesInWorkoutEntity

@Dao
interface ExerciseDao {
    @Query(
        "SELECT exercise.id, exercise.name, exercise.description, exercise.image, exercise.conditionsType, inWorkout.repeatTimes, inWorkout.secondsToDone, inWorkout.restSeconds, exercise.isDefault, exercise.isDeleted " +
                "FROM ExerciseEntity AS exercise " +
                "INNER JOIN ExercisesInWorkoutEntity AS inWorkout ON exercise.id == inWorkout.exerciseId " +
                "WHERE inWorkout.workoutId == :workoutId " +
                "ORDER BY inWorkout.number;"
    )
    fun getFor(workoutId: Long): List<ExerciseEntity>

    @Query(
        "SELECT exercise.id, exercise.name, exercise.description, exercise.image, exercise.conditionsType, inWorkout.repeatTimes, inWorkout.secondsToDone, inWorkout.restSeconds, exercise.isDefault, exercise.isDeleted " +
                "FROM ExerciseEntity AS exercise " +
                "INNER JOIN ExercisesInWorkoutEntity AS inWorkout ON exercise.id == inWorkout.exerciseId " +
                "WHERE inWorkout.workoutId == :workoutId AND inWorkout.number = :number " +
                "ORDER BY inWorkout.number " +
                "LIMIT 1;"
    )
    fun getFor(workoutId: Long, number: Int): ExerciseEntity

    @Query(
        "SELECT * FROM ExerciseEntity" +
                " WHERE NOT isDeleted")
    fun getAll(): List<ExerciseEntity>

    @Insert
    fun insert(exercise: ExerciseEntity): Long

    @Update
    fun update(exercise: ExerciseEntity)

    @Delete
    fun delete(exercise: ExerciseEntity)
}

