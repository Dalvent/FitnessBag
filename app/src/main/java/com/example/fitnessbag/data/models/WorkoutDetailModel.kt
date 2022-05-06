package com.example.fitnessbag.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutDetailModel(
    var id: Int,
    var name: String,
    var description: String,
    var image: String,
    var tags: List<String>,
    var exercises: List<ExerciseModel>
) : Parcelable