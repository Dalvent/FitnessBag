package com.example.fitnessbag.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TagEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var value: String
)