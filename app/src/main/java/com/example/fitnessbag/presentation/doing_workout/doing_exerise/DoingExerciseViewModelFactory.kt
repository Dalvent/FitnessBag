package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnessbag.data.models.ExerciseExecutionConditionsType
import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.presentation.doing_workout.WorkoutNavigator

class DoingExerciseViewModelFactory(private val workoutNavigator: WorkoutNavigator, private val exerciseModel: ExerciseModel) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(exerciseModel.executionConditionsType) {
            ExerciseExecutionConditionsType.TimeIsUp -> TimeDoingExerciseViewModel(
                workoutNavigator,
                exerciseModel
            ) as T
            ExerciseExecutionConditionsType.CompleteRepetition -> CompleteRepetitionDoingExerciseViewModel(
                workoutNavigator,
                exerciseModel
            ) as T
        }
    }
}