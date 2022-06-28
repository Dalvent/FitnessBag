package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.FragmentDoingExerciseBinding
import com.example.fitnessbag.domain.models.ExerciseConditionsType
import com.example.fitnessbag.presentation.doing_workout.BaseDoingWorkoutFragment
import com.example.fitnessbag.presentation.execute
import com.example.fitnessbag.presentation.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoingExerciseFragment : BaseDoingWorkoutFragment() {

    private val args: DoingExerciseFragmentArgs by navArgs()
    private val viewModel: DoingExerciseViewModel by viewModel()

    private var _binding: FragmentDoingExerciseBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.initialize(args.progress)
        
        _binding = FragmentDoingExerciseBinding.inflate(inflater, container, false)

        binding.exerciseProgressBar.progress = 0
        binding.exerciseProgressBar.interpolator = AccelerateInterpolator()
        
        binding.backImageView.setOnClickListener {
            this.onBackPressed()
        }
        
        viewModel.exercisesCount.observe(viewLifecycleOwner) {
            binding.workoutProgressProgressBar.max = it
        }
        
        viewModel.currentExerciseNumber.observe(viewLifecycleOwner) {
            binding.workoutProgressProgressBar.progress = it
            binding.exerciseNumberTextView.text = "${it}/${viewModel.exercisesCount.value}"
        }
        
        viewModel.canGoPrevious.observe(viewLifecycleOwner) {
            binding.previousButton.visibility = if(it) View.VISIBLE else View.INVISIBLE
        }
        
        viewModel.statusToDone.observe(viewLifecycleOwner) {
            binding.toDoneTextView.text = it
        }

        viewModel.specialButtonDrawable.observe(viewLifecycleOwner) {
            binding.specailButton.setImageDrawable(requireContext().getDrawable(it))
        }
        
        viewModel.name.observe(viewLifecycleOwner) {
            binding.titleTextView.text = it
        }
        
        viewModel.imageUrl.observe(viewLifecycleOwner) {
            binding.exercisesImageView.loadImage(it, R.drawable.no_image_exercise)
        }
        
        viewModel.exerciseProcessStatus.observe(viewLifecycleOwner) {
            if(it == ExerciseProcessStatus.Doing)
                return@observe
            
            if(viewModel.progress.isDonned()) {
                DoingExerciseFragmentDirections.actionDoingExerciseFragmentToDoneWorkoutFragment(viewModel.progress)
                    .execute(findNavController())
            } else if(viewModel.exercise.restSeconds == 0) {
                DoingExerciseFragmentDirections.actionDoingExerciseFragmentSelf(viewModel.progress)
                    .execute(findNavController())
            } else {
                DoingExerciseFragmentDirections.actionDoingExerciseFragmentToRestFragment(viewModel.progress, viewModel.exercise.restSeconds)
                    .execute(findNavController())
            }
        }

        binding.previousButton.setOnClickListener {
            viewModel.goPrevious()
        }

        binding.skipButton.setOnClickListener {
            viewModel.skip()
        }
        
        binding.specailButton.setOnClickListener {
            viewModel.pressedSpecialButton()
        }
        
        if(viewModel.strategy is TimerDoingExerciseStrategy) {
            binding.exerciseProgressBar.visibility = View.VISIBLE
            val timerStrategy = viewModel.strategy as TimerDoingExerciseStrategy
            
            timerStrategy.timerProgressMax.observe(viewLifecycleOwner) {
                binding.exerciseProgressBar.max = it
            }
            timerStrategy.timerProgress.observe(viewLifecycleOwner) {
                binding.exerciseProgressBar.progress = it
            }
        }
        else {
            binding.exerciseProgressBar.visibility = View.INVISIBLE
        }
        
        return binding.root
    }
}

