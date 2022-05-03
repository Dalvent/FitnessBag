package com.example.fitnessbag.presentation.doing_workout.rest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.fitnessbag.databinding.FragmentRestBinding
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutFragment


class RestFragment : Fragment() {
    
    companion object {
        const val DOING_EXERCISE_SECONDS = "DOING_EXERCISE_SECONDS"
    }
    
    private lateinit var binding: FragmentRestBinding
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestBinding.inflate(inflater, container, false)
        val workoutNavigator = (requireParentFragment() as DoingWorkoutFragment).workoutNavigator

        val restSeconds = arguments?.getInt(DOING_EXERCISE_SECONDS)!!
        val viewModel = ViewModelProvider(this, RestViewModelFactory(workoutNavigator, restSeconds)).get(RestViewModel::class.java)
        viewModel.remainingTime.observe(viewLifecycleOwner) {
            binding.timeTextView.text = it
        }
        
        binding.skipButton.setOnClickListener {
            viewModel.skip()
        }
        
        binding.addSecondsButton.setOnClickListener {
            viewModel.addSeconds(10)
        }
        
        return binding.root
    }
}

