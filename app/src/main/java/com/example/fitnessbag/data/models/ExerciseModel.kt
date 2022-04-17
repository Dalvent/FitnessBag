package com.example.fitnessbag.data.models

data class ExerciseModel(
    var id: Int,
    var name: String,
    var description: String,
    var image: String,
    var executionConditionsType: ExerciseExecutionConditionsType,
    var repeatTimes: Int,
    var secondsToDone: Int
)