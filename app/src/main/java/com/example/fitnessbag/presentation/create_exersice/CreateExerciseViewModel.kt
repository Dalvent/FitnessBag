package com.example.fitnessbag.presentation.create_exersice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessbag.domain.services.ImagePickerService
import com.example.fitnessbag.domain.models.ExerciseConditionsType
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.repositories.exercise.ExerciseRepository
import com.example.fitnessbag.presentation.BaseViewModel
import com.example.fitnessbag.utils.ValidationException

class CreateExerciseViewModel(private val exerciseRepository: ExerciseRepository, val imagePickerService: ImagePickerService) :
    BaseViewModel() {
    private val _name = MutableLiveData<String>()
    val name: MutableLiveData<String> = _name

    private val _description = MutableLiveData<String>()
    val description: MutableLiveData<String> = _description

    private val _exerciseExecutionConditionsType =
        MutableLiveData(ExerciseConditionsType.TimeIsUp)
    val exerciseConditionsType: MutableLiveData<ExerciseConditionsType> =
        _exerciseExecutionConditionsType

    private val _repeatTimes = MutableLiveData<Int>()
    val repeatTimes: MutableLiveData<Int> = _repeatTimes

    private val _secondsToDone = MutableLiveData<Int>()
    val secondsToDone: MutableLiveData<Int> = _secondsToDone

    private val _restSeconds = MutableLiveData<Int>()
    val restSeconds: MutableLiveData<Int> = _restSeconds

    var _pathUrl = MutableLiveData<String>("")
    val pathUrl: LiveData<String> get() = _pathUrl
    fun setUrl(value: String) {
        if(_pathUrl.value == value)
            return;

        _pathUrl.value = value
    }

    fun setName(value: String) {
        if (value == _name.value)
            return

        _name.value = value
    }

    fun setExerciseExecutionConditionsType(value: ExerciseConditionsType) {
        if (value == _exerciseExecutionConditionsType.value)
            return

        _exerciseExecutionConditionsType.value = value
    }

    fun setRepeatTimes(value: Int) {
        if (value == _repeatTimes.value)
            return

        _repeatTimes.value = value
    }

    fun setSecondsToDone(value: Int) {
        if (value == _secondsToDone.value)
            return

        _secondsToDone.value = value
    }

    fun setRestSeconds(value: Int) {
        if (value == _restSeconds.value)
            return

        _restSeconds.value = value
    }

    fun save(): Exercise {
        checkValidation()
        
        return when(exerciseConditionsType.value) {
            ExerciseConditionsType.TimeIsUp -> exerciseRepository.createNewTimeExercise(
                name.value ?: "",
                description.value ?: "",
                pathUrl.value ?: "",
                secondsToDone.value ?: 0,
                restSeconds.value ?: 0
            )
            ExerciseConditionsType.CompleteRepetition ->  exerciseRepository.createNewRepeatExercise(
                name.value ?: "",
                description.value ?: "",
                pathUrl.value ?: "",
                repeatTimes.value ?: 0,
                restSeconds.value ?: 0
            )
            null -> throw NotImplementedError()
        }
    }

    private fun checkValidation() {
        if (_name.value.isNullOrBlank())
            throw ValidationException("Fill name!")

        when (_exerciseExecutionConditionsType.value) {
            ExerciseConditionsType.TimeIsUp -> {
                if (_secondsToDone.value == null || _secondsToDone.value!! < 0)
                    throw ValidationException("Fill seconds to done!")
            }
            ExerciseConditionsType.CompleteRepetition -> {
                if (_repeatTimes.value == null || _repeatTimes.value!! < 0)
                    throw ValidationException("Fill repeat times!")
            }
        }

        if (_restSeconds.value == null || _restSeconds.value!! < 0)
            throw ValidationException("Fill rest seconds!!")
    }
}
