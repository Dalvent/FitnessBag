package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnessbag.domain.models.ExerciseConditionsType
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.RepeatExercise
import com.example.fitnessbag.domain.models.TimeExercise
import com.example.fitnessbag.presentation.doing_workout.WorkoutNavigator

class DoingExerciseViewModelFactory(private val workoutNavigator: WorkoutNavigator, private val exercise: Exercise) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(exercise) {
            is RepeatExercise -> {
                return CompleteRepetitionDoingExerciseViewModel(
                    workoutNavigator,
                    exercise
                ) as T
            }
            is TimeExercise -> {
                return TimeDoingExerciseViewModel(
                    workoutNavigator,
                    exercise
                ) as T
            }
        }
    }
}