package com.example.fitnesbag.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        var name: String,
        var description: String,
        var image: String,
        var conditionsType: Int,
        var repeatTimes: Int,
        var secondsToDone: Int,
        var restSeconds: Int
)
