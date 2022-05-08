package com.example.fitnessbag.domain.repositories.exercise

import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.RepeatExercise
import com.example.fitnessbag.domain.models.TimeExercise

interface ExerciseRepository {
    fun getAll(): List<Exercise>
    fun createNewTimeExercise(
        name: String,
        description: String,
        image: String,
        secondsToDone: Int,
        restSeconds: Int = 30
    ): TimeExercise
    fun createNewRepeatExercise(
        name: String,
        description: String,
        image: String,
        repeatTimes: Int,
        restSeconds: Int = 30
    ): RepeatExercise
}