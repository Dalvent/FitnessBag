package com.example.fitnessbag.data.repositories

import com.example.fitnessbag.data.models.WorkoutInCatalogModel

class FakeWorkoutInCatalogRepository : WorkoutInCatalogRepository {
    override suspend fun get(): List<WorkoutInCatalogModel> {
        return listOf(
            WorkoutInCatalogModel(
                1,
                "Body power",
                "The best training, the coolest",
                "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                listOf(
                    "abs",
                    "legs",
                    "head",
                    "fat burning",
                    "fat burning",
                    "fat burning",
                    "fat burning",
                    "fat burning",
                )
            ),
            WorkoutInCatalogModel(
                2,
                "Body 5",
                "The best training, the coolest",
                "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                listOf(
                    "abs",
                    "legs",
                    "head",
                    "fat burning",
                )
            ),
            WorkoutInCatalogModel(
                3,
                "Body power2",
                "The best training, the coolest",
                "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                listOf(
                    "abs",
                    "legs",
                    "head",
                    "fat burning",
                )
            ),
            WorkoutInCatalogModel(
                4,
                "Body powe3r",
                "The best training, the coolest",
                "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                listOf(
                    "abs",
                    "legs",
                    "head",
                    "fat burning",
                )
            ),
            WorkoutInCatalogModel(
                5,
                "Body powe1r",
                "The best training, the coolest",
                "https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg",
                listOf(
                    "abs",
                    "legs",
                    "head",
                    "fat burning",
                )
            )

        )
    }

}