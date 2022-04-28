package com.example.fitnessbag.data.repositories

import com.example.fitnessbag.data.models.ExerciseExecutionConditionsType
import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.data.models.WorkoutDetailModel

class FakeWorkoutDetailRepository : WorkoutDetailRepository {
    override fun getWorkoutDetail(id: Int): WorkoutDetailModel {
        return WorkoutDetailModel(
            1,
            "Body Power",
            "info info info info info info info info info",
            "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
            listOf(
                "abs",
                "legs",
                "head",
                "fat burning",
            ),
            listOf(
                ExerciseModel(
                    1,
                    "Антоха",
                    "DsfaF DFSAFADS",
                    "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                    ExerciseExecutionConditionsType.CompleteRepetition,
                    10,
                    0
                ),
                ExerciseModel(
                    2,
                    "Антоха",
                    "DsfaF DFSAFADS",
                    "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                    ExerciseExecutionConditionsType.CompleteRepetition,
                    10,
                    0
                ),
                ExerciseModel(
                    3,
                    "Антоха",
                    "DsfaF DFSAFADS",
                    "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                    ExerciseExecutionConditionsType.CompleteRepetition,
                    10,
                    0
                ),
                ExerciseModel(
                    4,
                    "Антоха",
                    "DsfaF DFSAFADS",
                    "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                    ExerciseExecutionConditionsType.CompleteRepetition,
                    10,
                    0
                ), ExerciseModel(
                    5,
                    "Антоха",
                    "DsfaF DFSAFADS",
                    "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                    ExerciseExecutionConditionsType.CompleteRepetition,
                    10,
                    0
                ),
                ExerciseModel(
                    6,
                    "Антоха",
                    "DsfaF DFSAFADS",
                    "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                    ExerciseExecutionConditionsType.CompleteRepetition,
                    10,
                    0
                ),
                ExerciseModel(
                    7,
                    "Антоха",
                    "DsfaF DFSAFADS",
                    "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                    ExerciseExecutionConditionsType.CompleteRepetition,
                    10,
                    0
                ),
                ExerciseModel(
                    8,
                    "Антоха",
                    "DsfaF DFSAFADS",
                    "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                    ExerciseExecutionConditionsType.CompleteRepetition,
                    10,
                    0
                ),
                ExerciseModel(
                    9,
                    "Антоха",
                    "DsfaF DFSAFADS",
                    "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                    ExerciseExecutionConditionsType.CompleteRepetition,
                    10,
                    0
                ),
                ExerciseModel(
                    10,
                    "Антоха",
                    "DsfaF DFSAFADS",
                    "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                    ExerciseExecutionConditionsType.CompleteRepetition,
                    10,
                    0
                )
            )
        )
    }

    override fun getExercise(workoutId: Int, exerciseNumber: Int) : ExerciseModel? {
        val exercises = getWorkoutDetail(workoutId).exercises
        if(exercises.size > exerciseNumber)
            return exercises[exerciseNumber]
        
        return null
    }
}