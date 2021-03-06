package com.example.fitnessbag.domain.models

import android.os.Parcelable
import com.example.fitnesbag.data.model.ExerciseEntity
import com.example.fitnessbag.App
import com.example.fitnessbag.R
import com.example.fitnessbag.data.entities.ExercisesInWorkoutEntity
import kotlinx.parcelize.Parcelize
import java.lang.RuntimeException

private val exerciseTypesString by lazy { App.instance.baseContext.resources.getStringArray(R.array.exercise_types_dropdown) }

enum class ExerciseConditionsType {
    TimeIsUp,
    CompleteRepetition
}

sealed interface Exercise : Parcelable {
    var id: Long?
    var name: String
    var description: String
    var image: String
    var restSeconds: Int
}

interface TimeExercise : Exercise {
    var secondsToDone: Int
}

interface RepeatExercise : Exercise {
    var repeatTimes: Int
}

@Parcelize
data class TimeExerciseData(
    override var id: Long? = null,
    override var name: String,
    override var description: String,
    override var image: String,
    override var secondsToDone: Int,
    override var restSeconds: Int = 30
) : TimeExercise

@Parcelize
data class RepeatExerciseData(
    override var id: Long? = null,
    override var name: String,
    override var description: String,
    override var image: String,
    override var repeatTimes: Int,
    override var restSeconds: Int = 30
) : RepeatExercise

fun Exercise.copy() : Exercise {
    if(this is RepeatExerciseData)
        return this.copy()
    
    if(this is TimeExerciseData)
        return this.copy()
    
    if(this is RepeatExercise) {
        return TimeExerciseData(id, name, description, image, repeatTimes, restSeconds)
    }
    
    if(this is TimeExercise) {
        return TimeExerciseData(id, name, description, image, secondsToDone, restSeconds)
    }
    
    throw IllegalArgumentException()
}

fun Exercise.toDoneToString() : String
{
    return when(this)
    {
        is RepeatExercise -> "x${this.repeatTimes}"
        is TimeExercise -> this.secondsToDone.toSecondsToDoneString()
    }
}

private fun Int.toSecondsToDoneString() : String {
    val seconds = this % 60
    val minutes = (this - seconds) / 60
    return "${minutes.toTwoDigitFormat()}:${seconds.toTwoDigitFormat()}"
}

private fun Int.toTwoDigitFormat() : String {
    var intString = this.toString()
    if(intString.length == 1)
        intString = "0" + intString
    return intString
}

fun ExerciseConditionsType.toInt(): Int {
    return when (this) {
        ExerciseConditionsType.TimeIsUp -> 0
        ExerciseConditionsType.CompleteRepetition -> 1
    }
}

fun ExerciseConditionsType.toText(): String {
    return exerciseTypesString[this.toInt()]
}

fun conditionTypeFrom(text: String): ExerciseConditionsType {
    return conditionTypeFrom(exerciseTypesString.indexOf(text))
}

fun conditionTypeFrom(value: Int): ExerciseConditionsType {
    return when (value) {
        0 -> ExerciseConditionsType.TimeIsUp
        1 -> ExerciseConditionsType.CompleteRepetition
        else -> throw IllegalArgumentException()
    }
}

fun Exercise.getToDone(): Int {
    return when (this) {
        is RepeatExercise -> this.repeatTimes
        is TimeExercise -> this.secondsToDone
    }
}

fun Exercise.toEntity(): ExerciseEntity {
    var secondsToDone = 0
    var repeatTimes = 0
    var conditionsType = ExerciseConditionsType.TimeIsUp

    if(this is TimeExercise) {
        secondsToDone = this.secondsToDone
        conditionsType = ExerciseConditionsType.TimeIsUp
    }
    if(this is RepeatExercise) {
        repeatTimes = this.repeatTimes
        conditionsType = ExerciseConditionsType.CompleteRepetition
    }

    return ExerciseEntity(this.id!!, this.name, this.description, this.image, conditionsType.toInt(), repeatTimes, secondsToDone, this.restSeconds, false, false)
}

fun Exercise.getConditionType(): ExerciseConditionsType {
    return when (this) {
        is RepeatExercise -> ExerciseConditionsType.CompleteRepetition
        is TimeExercise -> ExerciseConditionsType.TimeIsUp
    }
}

fun ExerciseEntity.toExercise(): Exercise {
    when (conditionTypeFrom(this.conditionsType) ) {
        ExerciseConditionsType.TimeIsUp -> {
            return TimeExerciseData(
                this.id,
                this.name,
                this.description,
                this.image,
                this.secondsToDone,
                this.restSeconds
            )
        }
        ExerciseConditionsType.CompleteRepetition -> {
            return RepeatExerciseData(
                this.id,
                this.name,
                this.description,
                this.image,
                this.repeatTimes,
                this.restSeconds
            )
        }
    }
}