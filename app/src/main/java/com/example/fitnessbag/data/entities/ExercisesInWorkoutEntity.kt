package com.example.fitnessbag.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.fitnesbag.data.model.ExerciseEntity
import com.example.fitnesbag.data.model.WorkoutEntity

@Entity(
    primaryKeys = ["exerciseId", "workoutId", "number"],
    indices = [
        Index(value = ["exerciseId"]),
        Index(value = ["workoutId"]),
        Index(value = ["number"]),
    ],
    foreignKeys = arrayOf(
    ForeignKey(
        entity = ExerciseEntity::class,
        parentColumns = ["id"],
        childColumns = ["exerciseId"]
    ),
    ForeignKey(
        entity = WorkoutEntity::class,
        parentColumns = ["id"],
        childColumns = ["workoutId"],
        onDelete = CASCADE
    )
))
class ExercisesInWorkoutEntity(
    var exerciseId: Long,
    var workoutId: Long,
    var number: Int,
    var repeatTimes: Int,
    var secondsToDone: Int,
    var restSeconds: Int
)