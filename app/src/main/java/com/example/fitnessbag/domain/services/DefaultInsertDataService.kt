package com.example.fitnessbag.domain.services

import com.example.fitnessbag.App
import com.example.fitnessbag.BuildConfig
import com.example.fitnessbag.R
import com.example.fitnessbag.domain.repositories.exercise.ExerciseRepository


interface DefaultInsertDataService {
    fun insertDefault()
}

class DefaultInsertDataServiceImpl(val exerciseRepository: ExerciseRepository) :
    DefaultInsertDataService {
    override fun insertDefault() {
        exerciseRepository.createNewRepeatExercise(
            App.instance.baseContext.getString(R.string.push_ups),
            "",
            getDrawablePath(R.drawable.push_ups),
            10,
            25,
            true
        )
        exerciseRepository.createNewRepeatExercise(
            App.instance.baseContext.getString(R.string.pull_ups),
            "",
            getDrawablePath(R.drawable.pull_ups),
            5,
            25,
            true
        )
        exerciseRepository.createNewRepeatExercise(
            App.instance.baseContext.getString(R.string.bodyweight_squat),
            "",
            getDrawablePath(R.drawable.bodyweight_squat),
            10,
            25,
            true
        )
        exerciseRepository.createNewRepeatExercise(
            App.instance.baseContext.getString(R.string.sit_ups),
            "",
            getDrawablePath(R.drawable.sit_ups),
            10,
            25,
            true
        )
        exerciseRepository.createNewRepeatExercise(
            "Шраги со штангой",
            "",
            getDrawablePath(R.drawable.shrugs_with_barbell),
            10,
            25,
            true
        )
        exerciseRepository.createNewRepeatExercise(
            "Выпады с гантелями",
            "",
            getDrawablePath(R.drawable.lunges_with_dumbbells),
            10,
            25,
            true
        )
        exerciseRepository.createNewRepeatExercise(
            "Сгибания рук в запястьях",
            "",
            getDrawablePath(R.drawable.wrist_curls),
            10,
            25,
            true
        )
        exerciseRepository.createNewRepeatExercise(
            "Отжимания на брусьях",
            "",
            getDrawablePath(R.drawable.push_ups_on_the_uneven_bars),
            10,
            25,
            true
        )
        exerciseRepository.createNewRepeatExercise(
            "Подъем гантелей перед собой",
            "",
            getDrawablePath(R.drawable.lifting_dumbbells_in_front_of_you),
            10,
            25,
            true
        )
        exerciseRepository.createNewRepeatExercise(
            "Разведение гантелей в наклоне",
            "",
            getDrawablePath(R.drawable.breeding_dumbbells_in_an_incline),
            10,
            25,
            true
        )
    }
    
    private fun getDrawablePath(drawableId: Int): String {
        return "android.resource://" + BuildConfig.APPLICATION_ID.toString() + "/" + drawableId
    }
}