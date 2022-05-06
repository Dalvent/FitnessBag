package com.example.fitnessbag.data.repositories

import com.example.fitnessbag.data.models.ExerciseExecutionConditionsType
import com.example.fitnessbag.data.models.ExerciseModel

interface ExerciseRepository {
    fun getExisted(): List<ExerciseModel>
    fun add(exerciseModel: ExerciseModel)
}

class FakeExerciseRepository : ExerciseRepository {
    val exercises = arrayListOf(
        ExerciseModel(
            1,
            "Отжимания",
            "DsfaF DFSAFADS",
            "https://tn.fishki.net/20/upload/post/201406/05/1275198/ed81817cdd78884ad5b125a9c3cdf8ae.jpg",
            ExerciseExecutionConditionsType.CompleteRepetition,
            10,
            0
        ),
        ExerciseModel(
            2,
            "Прыжки",
            "DsfaF DFSAFADS",
            "https://static6.depositphotos.com/1001951/625/i/600/depositphotos_6255367-stock-photo-handsome-man-jumping.jpg",
            ExerciseExecutionConditionsType.TimeIsUp,
            0,
            10
        ),
        ExerciseModel(
            3,
            "Покушать бутер",
            "DsfaF DFSAFADS",
            "https://primebeef.ru/images/cms/thumbs/a5b0aeaa3fa7d6e58d75710c18673bd7ec6d5f6d/img_3911_500_306_5_100.jpg",
            ExerciseExecutionConditionsType.CompleteRepetition,
            10,
            0
        ))

    override fun getExisted(): List<ExerciseModel> {
        return exercises
    }

    override fun add(exerciseModel: ExerciseModel) {
        exercises.add(exerciseModel)
    }
}