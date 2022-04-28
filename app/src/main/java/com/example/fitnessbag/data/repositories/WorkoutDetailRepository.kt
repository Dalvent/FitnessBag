package com.example.fitnessbag.data.repositories

import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.data.models.WorkoutDetailModel

interface WorkoutDetailRepository{
    fun getWorkoutDetail(id: Int): WorkoutDetailModel
    fun getExercise(workoutId: Int, exerciseNumber: Int): ExerciseModel?
}