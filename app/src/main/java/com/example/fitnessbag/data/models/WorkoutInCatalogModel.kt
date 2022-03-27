package com.example.fitnessbag.data.models

data class WorkoutInCatalogModel(
    var id: Int,
    var name: String,
    val description: String,
    val image: String,
    var tags: List<String>
)