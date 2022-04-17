package com.example.fitnessbag

import android.app.Application
import com.example.fitnessbag.data.repositories.FakeWorkoutDetailRepository
import com.example.fitnessbag.data.repositories.FakeWorkoutInCatalogRepository
import com.example.fitnessbag.data.repositories.WorkoutDetailRepository
import com.example.fitnessbag.data.repositories.WorkoutInCatalogRepository
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
        viewModel { WorkoutCatalogViewModel(get()) }
        viewModel { WorkoutDetailViewModel(get()) }
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