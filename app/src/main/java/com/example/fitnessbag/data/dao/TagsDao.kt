package com.example.fitnessbag.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessbag.data.entities.TagEntity
import com.example.fitnessbag.data.entities.TagsInWorkoutEntity

@Dao
interface TagsDao {
    @Query(
        "SELECT tag.id, tag.value " +
                "FROM TagEntity AS tag " +
                "INNER JOIN TagsInWorkoutEntity AS inWorkout ON tagId == inWorkout.tagId " +
                "WHERE id == :workoutId;"
    )
    fun getFor(workoutId: Long): List<TagEntity>
    
    @Query("SELECT * FROM TagEntity WHERE value IN(:tags)")
    fun getAlreadyAdded(tags: List<String>): List<TagEntity>
    
    @Insert
    fun insert(tags: TagEntity): Long

    @Insert
    fun insertRelations(relations: List<TagsInWorkoutEntity>)
}