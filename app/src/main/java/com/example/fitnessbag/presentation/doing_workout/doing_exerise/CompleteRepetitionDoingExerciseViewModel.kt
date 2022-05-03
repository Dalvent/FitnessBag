package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.presentation.doing_workout.WorkoutNavigator

class CompleteRepetitionDoingExerciseViewModel(private val workoutNavigator: WorkoutNavigator, private val exerciseModel: ExerciseModel
) : DoingExerciseViewModel() {

    init {
        _statusToDone.value = "x${exerciseModel.repeatTimes}"
        _name.value = exerciseModel.name
        _imageUrl.value = exerciseModel.image
        _specialButtonText.value = "Done!"
    }

    override fun pressedSpecialButton() {
        workoutNavigator.navigateNext()
    }
}