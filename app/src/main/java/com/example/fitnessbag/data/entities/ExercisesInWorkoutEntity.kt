package com.example.fitnessbag.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.fitnesbag.data.model.ExerciseEntity
import com.example.fitnesbag.data.model.WorkoutEntity

@Entity(
    primaryKeys = ["exerciseId", "workoutId"],
    indices = [
        Index(value = ["exerciseId"]),
        Index(value = ["workoutId"])
    ],
    foreignKeys = arrayOf(
    ForeignKey(
        entity = ExerciseEntity::class,
        parentColumns = ["id"],
        childColumns = ["exerciseId"],
        onDelete = CASCADE
    ),
    ForeignKey(
        entity = WorkoutEntity::class,
        parentColumns = ["id"],
        childColumns = ["workoutId"]
    )
))
class ExercisesInWorkoutEntity(
    var exerciseId: Long,
    var workoutId: Long
)