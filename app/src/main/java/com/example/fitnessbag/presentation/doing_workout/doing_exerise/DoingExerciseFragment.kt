package com.example.fitnessbag.presentation.doing_workout.doing_exerise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.databinding.FragmentDoingExerciseBinding
import com.example.fitnessbag.presentation.doing_workout.DoingWorkoutFragment
import com.example.fitnessbag.presentation.utils.loadImage

class DoingExerciseFragment : Fragment() {

    companion object {
        const val DOING_EXERCISE_MODEL = "DOING_EXERCISE_MODEL"
    }
    
    private var _binding: FragmentDoingExerciseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        _binding = FragmentDoingExerciseBinding.inflate(inflater, container, false)
        val workoutNavigator = (requireParentFragment() as DoingWorkoutFragment).workoutNavigator
        val exerciseModel = arguments?.getParcelable<ExerciseModel>(DOING_EXERCISE_MODEL)!!

        val viewModel = ViewModelProvider(this, DoingExerciseViewModelFactory(workoutNavigator, exerciseModel)).get(
            DoingExerciseViewModel::class.java)
        
        viewModel.statusToDone.observe(viewLifecycleOwner) {
            binding.toDoneTextView.text = it
        }

        viewModel.specialButtonText.observe(viewLifecycleOwner) {
            binding.specailButton.text = it
        }
        
        viewModel.name.observe(viewLifecycleOwner) {
            binding.titleTextView.text = it
        }
        
        viewModel.imageUrl.observe(viewLifecycleOwner) {
            binding.exercisesImageView.loadImage(it)
        }
        
        binding.specailButton.setOnClickListener {
            viewModel.pressedSpecialButton()
        }
        
        return binding.root
    }
}

