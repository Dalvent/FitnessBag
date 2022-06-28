package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessbag.R
import com.example.fitnessbag.domain.models.TimeExercise
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutProgress
import com.example.fitnessbag.presentation.workout_detail.toSecondsToDoneString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerDoingExerciseStrategy(
    val coroutineScope: CoroutineScope,
    exercise: TimeExercise,
    progress: DoingWorkoutProgress,
    doingExerciseViewModel: DoingExerciseViewModel
) : AbstractDoingExerciseStrategy<TimeExercise>(exercise, progress, doingExerciseViewModel) {

    companion object {
        private const val PROGRESS_INTERPOLATION = 10
    }
    
    private var timerJob: Job? = null
    private var remainingTime: Int = 0
    
    private var _timerProgress = MutableLiveData(0)
    var timerProgress: LiveData<Int> = _timerProgress

    private var _timerProgressMax = MutableLiveData(0)
    var timerProgressMax: LiveData<Int> = _timerProgressMax
    
    private fun isTimerStart() = timerJob != null
    
    init {
        remainingTime = exercise.secondsToDone
        doingExerciseViewModel.statusToDone.value = remainingTime.toSecondsToDoneString()
        startTimer()
        
        _timerProgressMax.value = exercise.secondsToDone * PROGRESS_INTERPOLATION
    }
    
    override fun pressedSpecialButton() {
        if(isTimerStart()) {
            stopTimer()
        }
        else {
            startTimer()
        }
    }

    private fun startTimer() {
        doingExerciseViewModel.specialButtonDrawable.value = R.drawable.ic_baseline_pause_24
        val delayTime = (1000 / PROGRESS_INTERPOLATION).toLong()
        timerJob = coroutineScope.launch { 
            while (remainingTime > 0) {
                for (i in 0 until PROGRESS_INTERPOLATION) {
                    delay(delayTime)
                    _timerProgress.value = _timerProgress.value!!.plus(1)
                }
                
                setSeconds(remainingTime - 1)
            }
            progress.doneExercise()
            doingExerciseViewModel.markDonned()
        }
    }

    private fun stopTimer() {
        doingExerciseViewModel.specialButtonDrawable.value = R.drawable.ic_baseline_play_arrow_24
        timerJob!!.cancel()
        timerJob = null
    }

    private fun setSeconds(seconds: Int) = synchronized(this) {
        remainingTime = seconds
        doingExerciseViewModel.statusToDone.value = remainingTime.toSecondsToDoneString()
    }
}