package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import com.example.fitnessbag.R
import com.example.fitnessbag.domain.models.RepeatExercise
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutProgress

class RepeatDoingExerciseStrategy(
    exercise: RepeatExercise,
    progress: DoingWorkoutProgress,
    doingExerciseViewModel: DoingExerciseViewModel
) : AbstractDoingExerciseStrategy<RepeatExercise>(exercise, progress, doingExerciseViewModel) {
    
    init {
        doingExerciseViewModel.statusToDone.value = "x${exercise.repeatTimes}"
        doingExerciseViewModel.specialButtonDrawable.value = R.drawable.ic_baseline_check_24
    }
    
    override fun pressedSpecialButton() {
        progress.doneExercise()
        doingExerciseViewModel.markDonned()
    }
}