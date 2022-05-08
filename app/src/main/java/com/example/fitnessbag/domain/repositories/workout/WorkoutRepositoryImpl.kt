package com.example.fitnessbag.domain.repositories.workout

import com.example.fitnesbag.data.model.WorkoutEntity
import com.example.fitnessbag.data.dao.ExerciseDao
import com.example.fitnessbag.data.dao.TagsDao
import com.example.fitnessbag.data.dao.WorkoutDao
import com.example.fitnessbag.data.entities.TagEntity
import com.example.fitnessbag.data.entities.TagsInWorkoutEntity
import com.example.fitnessbag.domain.models.*

class WorkoutRepositoryImpl(
    val workoutDao: WorkoutDao,
    val tagsDao: TagsDao,
    val exerciseDao: ExerciseDao
) : WorkoutRepository {

    override fun getDetailed(id: Long): WorkoutDetail {
        val workout = workoutDao.get(id)
        return WorkoutDetailData(
            workout.id,
            workout.name,
            workout.description,
            workout.image,
            tagsDao.getFor(workout.id!!).map { it.value },
            exerciseDao.getFor(workout.id!!).map { it.toExercise() }
        )
    }

    override fun getForCatalog(): List<Workout> {
        val workouts = workoutDao.getAll()

        val result = ArrayList<Workout>(workouts.count())

        for (workout in workouts) {
            result.add(WorkoutData(
                workout.id,
                workout.name,
                workout.description,
                workout.image,
                tagsDao.getFor(workout.id!!).map { it.value }
            ))
        }

        return result
    }

    override fun createNew(name: String, description: String, image: String, tags: List<String>, exercises: List<Exercise>): WorkoutDetail {
        val entity = WorkoutEntity(null, name, description, image)
        entity.id = workoutDao.insert(entity)
        val tagsEntities = applyTags(tags)
        
        
        val tagsRelations = tagsEntities.map { TagsInWorkoutEntity(it.id!!, entity.id!!) }
        
        tagsDao.insertRelations(tagsRelations)
        
        // HACK
        return getDetailed(entity.id!!)
    }

    private fun applyTags(tags: List<String>): List<TagEntity> {
        if(tags.isEmpty())
            return listOf()
        
        val added = tagsDao.getAlreadyAdded(tags)
        
        val newTags = tags.filter { tagString -> !added.any { it.value.equals(tagString) } }
            .map { TagEntity(null, it) }.toMutableList()
        
        for (newTag in newTags) {
            newTag.id = tagsDao.insert(newTag)
        }
        
        // HACK
        return tagsDao.getAlreadyAdded(tags)
    }
}