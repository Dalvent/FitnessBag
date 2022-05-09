package com.example.fitnessbag.data.dao

import androidx.room.*
import com.example.fitnesbag.data.model.WorkoutEntity
import com.example.fitnessbag.data.entities.DoneWorkoutEntity
import com.example.fitnessbag.data.entities.ExercisesInWorkoutEntity
import com.example.fitnessbag.data.query.DoneWorkoutQuery
import com.example.fitnessbag.domain.models.Workout

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM WorkoutEntity WHERE NOT isDeleted")
    fun getAll(): List<WorkoutEntity>
    
    @Query("SELECT * FROM WorkoutEntity WHERE id == :workoutId AND NOT isDeleted")
    fun get(workoutId: Long): WorkoutEntity

    @Query("SELECT workout.id, workout.name, workout.description, workout.image, workout.tags, donned.date " +
            "FROM WorkoutEntity as workout " +
            "INNER JOIN DoneWorkoutEntity AS donned ON donned.workoutId == workout.id ")
    fun getDonned(): List<DoneWorkoutQuery>
    
    @Insert
    fun insertDonned(exercise: DoneWorkoutEntity): Long
    
    @Insert
    fun insert(workoutEntity: WorkoutEntity): Long
    
    @Insert
    fun insertExercisesInWorkout(workoutExercises: List<ExercisesInWorkoutEntity>)

    @Update
    fun update(workoutEntity: WorkoutEntity)
}