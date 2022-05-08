package com.example.fitnessbag.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface Workout : Parcelable {
    var id: Long?
    var name: String
    var description: String
    var image: String
    var tags: List<String>
}


interface WorkoutDetail : Workout {
    var exercises: List<Exercise>
}

@Parcelize
data class WorkoutData(
    override var id: Long? = null,
    override var name: String,
    override var description: String,
    override var image: String,
    override var tags: List<String>,
) : Workout

@Parcelize
data class WorkoutDetailData(
    override var id: Long? = null,
    override var name: String,
    override var description: String,
    override var image: String,
    override var tags: List<String>,
    override var exercises: List<Exercise>
) : WorkoutDetail