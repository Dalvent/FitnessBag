package com.example.fitnessbag.presentation.doing_workout.rest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import com.example.fitnessbag.R
import com.example.fitnessbag.data.repositories.WorkoutDetailRepository
import com.example.fitnessbag.databinding.FragmentDoingExerciseBinding
import com.example.fitnessbag.databinding.FragmentRestBinding
import com.example.fitnessbag.presentation.BaseViewModel
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutFragment
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutState

class RestFragment : Fragment() {

    private var _binding: FragmentRestBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestBinding.inflate(inflater, container, false)

        val parentViewModel = (requireParentFragment() as DoingWorkoutFragment).viewModel

        binding.skipButton.setOnClickListener {
            parentViewModel.updateState(DoingWorkoutState.Exercise)

        }

        return binding.root
    }
}

class RestViewModel() : BaseViewModel() {
    private val _doingWorkoutState = MutableLiveData<DoingWorkoutState>()
    val doingWorkoutState : LiveData<DoingWorkoutState> = _doingWorkoutState

    
    
    fun updateState(state: DoingWorkoutState) {
        _doingWorkoutState.value = state
    }
}