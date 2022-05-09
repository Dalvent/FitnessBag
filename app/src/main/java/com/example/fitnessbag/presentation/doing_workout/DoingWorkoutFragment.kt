package com.example.fitnessbag.presentation.doing_workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.DoingWorkoutFragmentBinding
import com.example.fitnessbag.presentation.CustomBackPressed
import com.example.fitnessbag.presentation.doing_workout.doing_exerise.DoingExerciseFragment
import com.example.fitnessbag.presentation.doing_workout.rest.RestFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class DoingWorkoutFragment() : Fragment(), CustomBackPressed {
    private val viewModel: DoingWorkoutViewModel by viewModel()
    private val args: DoingWorkoutFragmentArgs by navArgs()

    val workoutNavigator: WorkoutNavigator get() = viewModel

    private var _binding: DoingWorkoutFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DoingWorkoutFragmentBinding.inflate(inflater, container, false)

        binding.backImageView.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure want to leave?")
            builder.setPositiveButton("Yes",) { var1, var2 ->
                findNavController().navigateUp()
            }
            builder.setNegativeButton("No") { var1, var2 ->
            }
            builder.create().show()
        }
        viewModel.initialize(args.workoutDetail)

        viewModel.doingWorkoutState.observe(viewLifecycleOwner) {
            navigateByState(it)
        }

        return binding.root
    }

    private fun navigateByState(it: DoingWorkoutState) {
        when (it) {
            DoingWorkoutState.Exercise -> {
                childFragmentManager.commit {
                    val bundle = Bundle()
                    bundle.putParcelable(
                        DoingExerciseFragment.DOING_EXERCISE_MODEL,
                        viewModel!!.currentExercise()
                    )
                    replace<DoingExerciseFragment>(
                        R.id.doingWorkoutFragmentContainer,
                        args = bundle
                    )
                }
            }
            DoingWorkoutState.Rest -> {
                childFragmentManager.commit {
                    val bundle = Bundle()
                    val restSeconds = viewModel.currentExercise()!!.restSeconds
                    bundle.putInt(RestFragment.DOING_EXERCISE_SECONDS, restSeconds)
                    replace<RestFragment>(R.id.doingWorkoutFragmentContainer, args = bundle)
                }
            }
            DoingWorkoutState.End -> {
                val action =
                    DoingWorkoutFragmentDirections.actionDoingWorkoutFragmentToDoneWorkoutFragment(
                        args.workoutDetail
                    )
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()

        getActionBar()?.hide()
    }

    override fun onStop() {
        super.onStop()

        getActionBar()?.show()
    }

    private fun getActionBar() = getCompatActivity().supportActionBar

    private fun getCompatActivity() = (activity as AppCompatActivity)
    
    override fun onBackPressed(): Boolean {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure want to leave?")
        builder.setPositiveButton("Yes",) { var1, var2 ->
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") { var1, var2 ->
        }
        val alertDialog = builder.create()
        alertDialog.show()
        
        return false
    }
}