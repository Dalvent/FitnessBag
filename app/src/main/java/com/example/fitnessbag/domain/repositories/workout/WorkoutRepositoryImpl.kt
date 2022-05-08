package com.example.fitnessbag.domain.repositories.workout

import com.example.fitnesbag.data.model.WorkoutEntity
import com.example.fitnessbag.data.dao.ExerciseDao
import com.example.fitnessbag.data.dao.WorkoutDao
import com.example.fitnessbag.domain.models.*

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
        val entity = WorkoutEntity(null, name, description, image, tags.joinToString(separator = "|"))
        entity.id = workoutDao.insert(entity)
        
        return toDetail(entity)
    }

    override fun delete(workout: Workout) {
        workoutDao.delete(workout.id!!)
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
}
