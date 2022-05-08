package com.example.fitnessbag.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.fitnesbag.data.model.ExerciseEntity
import com.example.fitnesbag.data.model.WorkoutEntity

@Entity(
    primaryKeys = ["tagId", "workoutId"],
    indices = [
        Index(value = ["tagId"]),
        Index(value = ["workoutId"])
    ],
    foreignKeys = arrayOf(
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"]
        )
    ))
data class TagsInWorkoutEntity(
    var tagId: Long,
    var workoutId: Long
)