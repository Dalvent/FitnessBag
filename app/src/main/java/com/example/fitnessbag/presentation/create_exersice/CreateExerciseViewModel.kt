package com.example.fitnessbag.presentation.create_exersice

import androidx.lifecycle.MutableLiveData
import com.example.fitnessbag.data.models.ExerciseExecutionConditionsType
import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.data.repositories.ExerciseRepository
import com.example.fitnessbag.presentation.BaseViewModel
import com.example.fitnessbag.utils.ValidationException

class CreateExerciseViewModel(private val exerciseRepository: ExerciseRepository) : BaseViewModel() {
    private val _name = MutableLiveData<String>()
    val name: MutableLiveData<String> = _name
    
    private val _description = MutableLiveData<String>()
    val description: MutableLiveData<String> = _description
    
    private val _exerciseExecutionConditionsType = MutableLiveData<ExerciseExecutionConditionsType>(ExerciseExecutionConditionsType.TimeIsUp)
    val exerciseExecutionConditionsType: MutableLiveData<ExerciseExecutionConditionsType> = _exerciseExecutionConditionsType

    private val _repeatTimes = MutableLiveData<Int>()
    val repeatTimes: MutableLiveData<Int> = _repeatTimes
    
    private val _secondsToDone = MutableLiveData<Int>()
    val secondsToDone: MutableLiveData<Int> = _secondsToDone

    private val _restSeconds = MutableLiveData<Int>()
    val restSeconds: MutableLiveData<Int> = _restSeconds
    
    fun setName(value: String) {
        if(value == _name.value)
            return

        _name.value = value
    }

    fun setDescription(value: String) {
        if(value == _description.value)
            return

        _description.value = value
    }

    fun setExerciseExecutionConditionsType(value: ExerciseExecutionConditionsType) {
        if(value == _exerciseExecutionConditionsType.value)
            return

        _exerciseExecutionConditionsType.value = value
    }

    fun setRepeatTimes(value: Int) {
        if(value == _repeatTimes.value)
            return

        _repeatTimes.value = value
    }

    fun setSecondsToDone(value: Int) {
        if(value == _secondsToDone.value)
            return

        _secondsToDone.value = value
    }

    fun setRestSeconds(value: Int) {
        if(value == _restSeconds.value)
            return

        _restSeconds.value = value
    }
    
    fun save() : ExerciseModel {
        if(_name.value == null || _name.value == "")
            throw ValidationException()

        if(_description.value == null || _description.value == "")
            throw ValidationException()

        if(_exerciseExecutionConditionsType.value == null)
            throw ValidationException()
        
        when(_exerciseExecutionConditionsType.value) {
            ExerciseExecutionConditionsType.TimeIsUp -> {
                if(_secondsToDone.value == null || _secondsToDone.value!! < 0)
                    throw ValidationException()       
            }
            ExerciseExecutionConditionsType.CompleteRepetition -> {
                if(_repeatTimes.value == null || _repeatTimes.value!! < 0)
                    throw ValidationException()
            }
        }
        
        if(_restSeconds.value == null || _restSeconds.value!! < 0)
            throw ValidationException()
        
        val exerciseModel = ExerciseModel(
            0,
            _name.value!!,
            _description.value!!,
            "",
            _exerciseExecutionConditionsType.value!!,
            _repeatTimes.value ?: 0,
            _secondsToDone.value ?: 0,
            _restSeconds.value!!
        )
        exerciseRepository.add(exerciseModel)
        
        return exerciseModel
    }
}