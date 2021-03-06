package com.example.fitnessbag

import android.app.Application
import androidx.room.Room
import com.example.fitnessbag.data.FitnessBagDatabase
import com.example.fitnessbag.data.dao.ExerciseDao
import com.example.fitnessbag.data.dao.WorkoutDao
import com.example.fitnessbag.domain.repositories.exercise.ExerciseRepository
import com.example.fitnessbag.domain.repositories.exercise.ExerciseRepositoryImpl
import com.example.fitnessbag.domain.repositories.workout.WorkoutRepository
import com.example.fitnessbag.domain.repositories.workout.WorkoutRepositoryImpl
import com.example.fitnessbag.domain.services.*
import com.example.fitnessbag.presentation.add_existed_exercise.AddExistedExerciseViewModel
import com.example.fitnessbag.presentation.create_exersice.CreateExerciseViewModel
import com.example.fitnessbag.presentation.create_workout.CreateWorkoutViewModel
import com.example.fitnessbag.presentation.doing_workout.doing_exerise.DoingExerciseViewModel
import com.example.fitnessbag.presentation.doing_workout.rest.RestViewModel
import com.example.fitnessbag.presentation.settings.SettingsViewModel
import com.example.fitnessbag.presentation.workout_detail.WorkoutDetailViewModel
import com.example.fitnessbag.presentation.workout_history.WorkoutHistoryViewModel
import com.example.fitnessbag.presentation.workouts_catalog.WorkoutCatalogViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class App() : Application() {

    companion object {
        lateinit var instance: App
    }
    
    override fun onCreate() {
        instance = this
        super.onCreate()
        
        val database = Room.databaseBuilder(
            baseContext,
            FitnessBagDatabase::class.java,
            "FitnessBagDatabase"
        ).allowMainThreadQueries().build()

        val module = module {
            single<FitnessBagDatabase> { database }
            single<ExerciseDao> { database.getExerciseDao() }
            single<WorkoutDao> { database.getWorkoutDao() }

            single<ExerciseRepository> { ExerciseRepositoryImpl(get()) }
            single<WorkoutRepository> { WorkoutRepositoryImpl(get(), get()) }
            single<ImagePickerService> { DhavalImagePickerService() }
            single<DefaultInsertDataService> { DefaultInsertDataServiceImpl(get()) }
            single<AppInitService> { AppInitServiceImpl(get()) }
            single<DbClearService> { DbClearServiceImpl(get()) }

            viewModel { WorkoutCatalogViewModel(get(), get()) }
            viewModel { WorkoutDetailViewModel(get()) }
            viewModel { AddExistedExerciseViewModel(get()) }
            viewModel { CreateExerciseViewModel(get(), get()) }
            viewModel { CreateWorkoutViewModel(get(), get()) }
            viewModel { MainViewModel(get(), get()) }
            viewModel { RestViewModel(get()) }
            viewModel { WorkoutHistoryViewModel(get()) }
            viewModel { DoingExerciseViewModel(get()) }
            viewModel { SettingsViewModel(get()) }
        }

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(module)
        }
    }
}