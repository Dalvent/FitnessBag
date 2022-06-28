package com.example.fitnessbag.presentation.doing_workout.rest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.FragmentRestBinding
import com.example.fitnessbag.domain.models.toDoneToString
import com.example.fitnessbag.presentation.doing_workout.BaseDoingWorkoutFragment
import com.example.fitnessbag.presentation.execute
import com.example.fitnessbag.presentation.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel


class RestFragment : BaseDoingWorkoutFragment() {

    private val args: RestFragmentArgs by navArgs()
    private val viewModel: RestViewModel by viewModel()
    
    private lateinit var binding: FragmentRestBinding
    
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.initialize(args.progress, args.restSeconds)
        binding = FragmentRestBinding.inflate(inflater, container, false)
        
        viewModel.remainingTime.observe(viewLifecycleOwner) {
            binding.timeTextView.text = it
        }

        viewModel.isTimerExpired.observe(viewLifecycleOwner) {
            if(!it)
                return@observe
            
            RestFragmentDirections.actionRestFragmentToDoingExerciseFragment(args.progress)
                .execute(findNavController())
        }

        viewModel.exercisesCount.observe(viewLifecycleOwner) {
            binding.exerciseNumberTextView.text = "${viewModel.currentExerciseNumber.value}/${it}"
        }

        viewModel.currentExerciseNumber.observe(viewLifecycleOwner) {
            binding.exerciseNumberTextView.text = "${it}/${viewModel.exercisesCount.value}"
        }
        
        viewModel.nextExercise.observe(viewLifecycleOwner) {
            binding.nextExerciseImageView.loadImage(it.image, R.drawable.no_image_exercise)
            binding.nextExerciseNameTextView.text = it.name
            binding.nextExerciseRestSecondsTextView.text = it.restSeconds.toString()
            binding.nextExerciseToDoneTextView.text = it.toDoneToString()
        }
        
        binding.skipButton.setOnClickListener {
            viewModel.skip()
        }
        
        binding.addSecondsButton.setOnClickListener {
            viewModel.addSeconds(10)
        }

        binding.backImageView.setOnClickListener {
            this.onBackPressed()
        }
        
        return binding.root
    }
}

