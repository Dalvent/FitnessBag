package com.example.fitnessbag.domain.repositories.exercise

import com.example.fitnesbag.data.model.ExerciseEntity
import com.example.fitnessbag.data.dao.ExerciseDao
import com.example.fitnessbag.domain.models.*

class ExerciseRepositoryImpl(val exerciseDao: ExerciseDao) : ExerciseRepository {
    override fun getAll(): List<Exercise> {
        return exerciseDao.getAll().map { it.toExercise() }
    }

    override fun createNewTimeExercise(
        name: String,
        description: String,
        image: String,
        secondsToDone: Int,
        restSeconds: Int
    ): TimeExercise {
        val entity = ExerciseEntity(
            null,
            name,
            description,
            image,
            ExerciseConditionsType.TimeIsUp.toInt(),
            0,
            secondsToDone,
            restSeconds,
            false
        )
        entity.id = exerciseDao.insert(entity)

        return entity.toExercise() as TimeExercise
    }

    override fun createNewRepeatExercise(
        name: String,
        description: String,
        image: String,
        repeatTimes: Int,
        restSeconds: Int
    ): RepeatExercise {
        val entity = ExerciseEntity(
            null,
            name,
            description,
            image,
            ExerciseConditionsType.CompleteRepetition.toInt(),
            repeatTimes,
            0,
            restSeconds,
            false
        )
        entity.id = exerciseDao.insert(entity)

        return entity.toExercise() as RepeatExercise
    }

    override fun remove(exercise: Exercise) {
        val exerciseEntity = exercise.toEntity()
        exerciseEntity.isDeleted = true
        exerciseDao.update(exerciseEntity)
    }
}