package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.fitnessbag.databinding.FragmentDoingExerciseBinding
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutFragment
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutState
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutViewModel

class DoingExerciseFragment : Fragment() {

    private var _binding: FragmentDoingExerciseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        _binding = FragmentDoingExerciseBinding.inflate(inflater, container, false)
        
        val parentViewModel = (requireParentFragment() as DoingWorkoutFragment).viewModel
        
        binding.doneButton.setOnClickListener {
            parentViewModel.updateState(DoingWorkoutState.Rest)
            
        }
        
        return binding.root
    }
}