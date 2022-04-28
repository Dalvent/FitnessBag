package com.example.fitnessbag.presentation.doing_workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.DoingWorkoutFragmentBinding
import com.example.fitnessbag.presentation.doing_workout.doing_exerise.DoingExerciseFragment
import com.example.fitnessbag.presentation.doing_workout.rest.RestFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoingWorkoutFragment() : Fragment() {

    val viewModel: DoingWorkoutViewModel by viewModel()
    
    private var _binding: DoingWorkoutFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DoingWorkoutFragmentBinding.inflate(inflater, container, false)
        
        getActionBar()?.hide()
        
        binding.backImageView.setOnClickListener {
            findNavController().navigateUp()
        }
        
        viewModel.doingWorkoutState.observe(viewLifecycleOwner) {
            navigateByState(it)
        }
        navigateByState(DoingWorkoutState.Exercise)
        
        return binding.root
    }

    private fun navigateByState(it: DoingWorkoutState) {
        when (it) {
            DoingWorkoutState.Exercise -> {
                childFragmentManager.commit {
                    replace<DoingExerciseFragment>(R.id.doingWorkoutFragmentContainer)
                }
            }
            DoingWorkoutState.Rest -> {
                childFragmentManager.commit {
                    replace<RestFragment>(R.id.doingWorkoutFragmentContainer)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getActionBar()?.show()
    }

    private fun getActionBar() = getCompatActivity().supportActionBar

    private fun getCompatActivity() = (activity as AppCompatActivity)
}