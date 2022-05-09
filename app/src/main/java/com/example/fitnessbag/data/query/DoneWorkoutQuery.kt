package com.example.fitnessbag.data.query

data class DoneWorkoutQuery(
    var id: Long,
    var name: String,
    var description: String,
    var image: String,
    var tags: String,
    var date: Long
)