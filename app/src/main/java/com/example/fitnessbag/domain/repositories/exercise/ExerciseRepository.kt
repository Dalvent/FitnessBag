package com.example.fitnessbag.domain.repositories.exercise

import com.example.fitnesbag.data.model.ExerciseEntity
import com.example.fitnessbag.domain.models.*
import java.util.*

interface ExerciseRepository {
    fun getAll(): List<Exercise>
    fun getInWorkout(workoutId: Long, number: Int): Exercise
    fun createNewTimeExercise(
        name: String,
        description: String,
        image: String,
        secondsToDone: Int,
        restSeconds: Int = 30,
        default: Boolean = false
    ): TimeExercise
    fun createNewRepeatExercise(
        name: String,
        description: String,
        image: String,
        repeatTimes: Int,
        restSeconds: Int = 30,
        default: Boolean = false
    ): RepeatExercise
    fun remove(exercise: Exercise)
}