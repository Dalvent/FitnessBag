package com.example.fitnessbag.data.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExerciseModel(
    var id: Int,
    var name: String,
    var description: String,
    var image: String,
    var executionConditionsType: ExerciseExecutionConditionsType,
    var repeatTimes: Int,
    var secondsToDone: Int,
    var restSeconds: Int = 30
) : Parcelable