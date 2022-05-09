package com.example.fitnesbag.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        var name: String,
        var description: String,
        var image: String,
        var tags: String,
        var isDeleted: Boolean
)
