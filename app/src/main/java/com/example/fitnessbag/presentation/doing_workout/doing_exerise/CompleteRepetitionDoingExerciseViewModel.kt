package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.RepeatExercise
import com.example.fitnessbag.domain.models.TimeExercise
import com.example.fitnessbag.presentation.doing_workout.WorkoutNavigator

class CompleteRepetitionDoingExerciseViewModel(private val workoutNavigator: WorkoutNavigator, private val exercise: RepeatExercise
) : DoingExerciseViewModel() {

    init {
        _statusToDone.value = "x${exercise.repeatTimes}"
        _name.value = exercise.name
        _imageUrl.value = exercise.image
        _specialButtonText.value = "Done!"
    }

    override fun pressedSpecialButton() {
        workoutNavigator.navigateNext()
    }
}