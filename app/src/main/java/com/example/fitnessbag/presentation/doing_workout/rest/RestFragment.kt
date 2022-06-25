package com.example.fitnessbag.presentation.doing_workout.rest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.example.fitnessbag.databinding.FragmentRestBinding


class RestFragment : Fragment() {

    private val args: RestFragmentArgs by navArgs()
    
    private lateinit var binding: FragmentRestBinding
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestBinding.inflate(inflater, container, false)
        val workoutNavigator = (requireParentFragment() as DoingWorkoutFragment).workoutNavigator

        val viewModel = ViewModelProvider(this, RestViewModelFactory(workoutNavigator, args.restSeconds)).get(RestViewModel::class.java)
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

