package com.example.fitnessbag.domain

import android.net.Uri
import com.example.fitnessbag.App
import com.example.fitnessbag.BuildConfig
import com.example.fitnessbag.R
import com.example.fitnessbag.domain.repositories.exercise.ExerciseRepository


interface DefaultInsertDataService {
    fun insertDefault()
}

class DefaultInsertDataServiceImpl(val exerciseRepository: ExerciseRepository) : DefaultInsertDataService {
    override fun insertDefault() {
        exerciseRepository.createNewRepeatExercise(
            App.instance.baseContext.getString(R.string.push_ups),
            "",
            getDrawablePath(R.drawable.push_ups),
            10,
            25
        )
        exerciseRepository.createNewRepeatExercise(
            App.instance.baseContext.getString(R.string.pull_ups),
            "",
            getDrawablePath(R.drawable.pull_ups),
            5,
            25
        )
        exerciseRepository.createNewRepeatExercise(
            App.instance.baseContext.getString(R.string.bodyweight_squat),
            "",
            getDrawablePath(R.drawable.bodyweight_squat),
            10,
            25
        )
        exerciseRepository.createNewRepeatExercise(
            App.instance.baseContext.getString(R.string.sit_ups),
            "",
            getDrawablePath(R.drawable.sit_ups),
            10,
            25
        )
    }
    
    private fun getDrawablePath(drawableId: Int): String {
        return "android.resource://" + BuildConfig.APPLICATION_ID.toString() + "/" + drawableId
    }
}