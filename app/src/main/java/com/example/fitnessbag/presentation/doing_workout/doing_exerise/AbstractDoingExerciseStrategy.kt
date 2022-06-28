package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutProgress

interface DoingExerciseStrategy {
    fun pressedSpecialButton()
}

abstract class AbstractDoingExerciseStrategy<TExercise: Exercise>(
    val exercise: TExercise,
    val progress: DoingWorkoutProgress,
    val doingExerciseViewModel: DoingExerciseViewModel
    ) : DoingExerciseStrategy {
    abstract override fun pressedSpecialButton()
}