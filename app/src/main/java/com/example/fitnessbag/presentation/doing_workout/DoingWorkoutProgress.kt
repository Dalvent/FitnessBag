package com.example.fitnessbag.presentation.doing_workout

import android.os.Parcelable
import com.example.fitnessbag.domain.models.WorkoutDetail
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DoingWorkoutProgress(
    val workoutId: Long,
    val exercisesCount: Int,
    val startTime: java.util.Date,
    var currentExerciseNumber: Int,
    val donnedExerciseNumbers: MutableList<Int>,
    val skippedExerciseNumbers: MutableList<Int>
) : Parcelable {

    companion object {
        fun startNewProgress(workout: WorkoutDetail): DoingWorkoutProgress {
            return DoingWorkoutProgress(
                workout.id!!,
                workout.exercises.size,
                Calendar.getInstance().time,
                1,
                mutableListOf(),
                mutableListOf())
        }
    }

    fun isDonned() = exercisesCount < currentExerciseNumber
    
    fun doneExercise() {
        donnedExerciseNumbers.add(currentExerciseNumber)
        currentExerciseNumber++
    }

    fun skipExercise() {
        skippedExerciseNumbers.add(currentExerciseNumber)
        currentExerciseNumber++
    }

    fun previousExercise() {
        if(skippedExerciseNumbers.contains(currentExerciseNumber))
            skippedExerciseNumbers.remove(currentExerciseNumber)
        else
            donnedExerciseNumbers.remove(currentExerciseNumber)

        this@DoingWorkoutProgress.currentExerciseNumber -= 1
    }
}