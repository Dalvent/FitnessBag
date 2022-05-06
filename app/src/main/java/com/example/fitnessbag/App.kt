package com.example.fitnessbag

import android.app.Application
import com.example.fitnessbag.data.repositories.*
import com.example.fitnessbag.presentation.add_existed_exercise.AddExistedExerciseViewModel
import com.example.fitnessbag.presentation.create_exersice.CreateExerciseViewModel
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutViewModel
import com.example.fitnessbag.presentation.workout_detail.WorkoutDetailViewModel
import com.example.fitnessbag.presentation.workouts_catalog.WorkoutCatalogViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class App() : Application() {
    val module = module {
        single<WorkoutInCatalogRepository> { FakeWorkoutInCatalogRepository() }
        single<WorkoutDetailRepository> { FakeWorkoutDetailRepository() }
        single<ExerciseRepository> { FakeExerciseRepository() }
        viewModel { WorkoutCatalogViewModel(get()) }
        viewModel { WorkoutDetailViewModel(get()) }
        viewModel { DoingWorkoutViewModel(get()) }
        viewModel { AddExistedExerciseViewModel(get()) }
        viewModel { CreateExerciseViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(module)
        }
    }
}