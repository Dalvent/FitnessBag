package com.example.fitnessbag.domain.models

import java.util.*

interface DoneWorkout {
    var id: Long?
    var name: String
    var description: String
    var image: String
    var tags: List<String>
    var date: Date
}

data class DoneWorkoutData(
    override var id: Long?,
    override var name: String,
    override var description: String,
    override var image: String,
    override var tags: List<String>,
    override var date: Date
) : DoneWorkout