package com.example.fitnessbag.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.fitnesbag.data.model.ExerciseEntity
import com.example.fitnesbag.data.model.WorkoutEntity
import java.util.*

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"]
        )
    )
)
data class DoneWorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var workoutId: Long,
    var date: Long
)