package com.example.fitnessbag.domain.repositories.workout

import com.example.fitnesbag.data.model.WorkoutEntity
import com.example.fitnessbag.data.dao.ExerciseDao
import com.example.fitnessbag.data.dao.WorkoutDao
import com.example.fitnessbag.data.entities.DoneWorkoutEntity
import com.example.fitnessbag.data.entities.ExercisesInWorkoutEntity
import com.example.fitnessbag.domain.models.*
import java.util.*
import kotlin.collections.ArrayList

class WorkoutRepositoryImpl(
    val workoutDao: WorkoutDao,
    val exerciseDao: ExerciseDao
) : WorkoutRepository {
    companion object {
        private const val tagSeparator = "|"
    }

    override fun getDetailed(id: Long): WorkoutDetail {
        val workout = workoutDao.get(id)
        return toDetail(workout)
    }


    override fun getForCatalog(): List<Workout> {
        val workouts = workoutDao.getAll()

        val result = ArrayList<Workout>(workouts.count())

        for (workout in workouts) {
            val tags = splitTags(workout.tags)
            result.add(WorkoutData(
                workout.id,
                workout.name,
                workout.description,
                workout.image,
                tags
            ))
        }

        return result
    }

    private fun splitTags(tags1: String) = tags1.split(tagSeparator).filter { it != "" }

    override fun createNew(name: String, description: String, image: String, tags: List<String>, exercises: List<Exercise>): WorkoutDetail {
        val entity = WorkoutEntity(null, name, description, image, tags.joinToString(separator = "|"), false)
        entity.id = workoutDao.insert(entity)
        
        var exercisesNumber = 1
        workoutDao.insertExercisesInWorkout(exercises.map { convertToExercisesInWorkout(it, entity.id!!, exercisesNumber++) })
        
        return toDetail(entity)
    }
    
    fun convertToExercisesInWorkout(exercise: Exercise, workoutId: Long, exercisesNumber: Int): ExercisesInWorkoutEntity {
        var secondsToDone = 0
        var repeatTimes = 0 
        
        if(exercise is TimeExercise)
            secondsToDone = exercise.secondsToDone
        if(exercise is RepeatExercise) 
            repeatTimes = exercise.repeatTimes

        return ExercisesInWorkoutEntity(exercise.id!!, workoutId, exercisesNumber, repeatTimes, secondsToDone, exercise.restSeconds)
    }

    override fun delete(workout: Workout) {
        val workoutEntity = toEntity(workout)
        workoutEntity.isDeleted = true
        workoutDao.update(workoutEntity)
    }

    override fun applyDonned(workout: Workout, date: Date) {
        workoutDao.insertDonned(DoneWorkoutEntity(null, workout.id!!, date.time))
    }

    override fun getDonned(): List<DoneWorkout> {
        return workoutDao
            .getDonned()
            .map { DoneWorkoutData(it.id, it.name, it.description, it.image, splitTags(it.tags), Date(it.date)) }
    }

    private fun toDetail(workout: WorkoutEntity): WorkoutDetailData {
        return WorkoutDetailData(
            workout.id,
            workout.name,
            workout.description,
            workout.image,
            splitTags(workout.tags),
            exerciseDao.getFor(workout.id!!).map { it.toExercise() }
        )
    }
    
    private fun toEntity(workout: Workout): WorkoutEntity {
        return WorkoutEntity(
            workout.id,
            workout.name,
            workout.description,
            workout.image,
            workout.tags.joinToString(separator = "|"),
            false
        )
    }
}
