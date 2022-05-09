package com.example.fitnessbag.presentation.create_exersice

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.fitnessbag.R
import com.example.fitnessbag.domain.models.ExerciseConditionsType
import com.example.fitnessbag.databinding.CreateExerciseFragmentBinding
import com.example.fitnessbag.domain.models.conditionTypeFrom
import com.example.fitnessbag.domain.models.toInt
import com.example.fitnessbag.presentation.create_workout.CreateWorkoutFragment
import com.example.fitnessbag.presentation.utils.loadImage
import com.example.fitnessbag.utils.ValidationException
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateExerciseFragment : Fragment() {
    
    private var _binding: CreateExerciseFragmentBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: CreateExerciseViewModel by viewModel()
    
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateExerciseFragmentBinding.inflate(inflater, container, false)
        
        binding.nameTextInputEditText.addTextChangedListener {
            viewModel.setName(it.toString())
        }
        binding.countTextInputEditText.addTextChangedListener {
            val valueString = it?.toString()
            if(valueString.isNullOrBlank())
                viewModel.setRepeatTimes(0)
            else
                viewModel.setRepeatTimes(valueString.toInt())
        }
        binding.secondsTextInputEditText.addTextChangedListener {
            val valueString = it?.toString()
            if(valueString.isNullOrBlank())
                viewModel.setSecondsToDone(0)
            else
                viewModel.setSecondsToDone(valueString.toInt())
        }
        binding.restSecondsTextInputEditText.addTextChangedListener {
            val valueString = it?.toString()
            if(valueString.isNullOrBlank())
                viewModel.setRestSeconds(0)
            else
                viewModel.setRestSeconds(valueString.toInt())
        }
        binding.exerciseTypeDropDown.addTextChangedListener {
            viewModel.setExerciseExecutionConditionsType(conditionTypeFrom(it.toString()))
        }
        binding.loadImageButton.setOnClickListener {
            viewModel.imagePickerService.pickImage { uri ->
                viewModel.setUrl(uri.toString())
            }
        }
        binding.resetImageButton.setOnClickListener {
            viewModel.setUrl("")
        }
        binding.addButton.setOnClickListener {
            try {
                val model = viewModel.save()

                val bundle = Bundle()
                bundle.putParcelable(CreateWorkoutFragment.SELECTED_EXERCISE, model)
                setFragmentResult(CreateWorkoutFragment.SELECTED_EXERCISE, bundle)
                findNavController().navigateUp()
            }
            catch (ex: ValidationException) {
                Toast.makeText(requireContext(), ex.reason, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        viewModel.pathUrl.observe(viewLifecycleOwner) {
            binding.exerciseImageView.loadImage(it, R.drawable.no_image_exercise)
            
            binding.resetImageButton.isEnabled = it != ""
        }
        
        viewModel.name.observe(viewLifecycleOwner) {
            if((binding.nameTextInputEditText.text?.toString()?.equals(it)) == true)
                return@observe
            
            binding.nameTextInputEditText.setText(it)
        }
        viewModel.description.observe(viewLifecycleOwner) {
            if((binding.descriptionTextInputEditText.text?.toString()?.equals(it)) == true)
                return@observe
            
            binding.descriptionTextInputEditText.setText(it)
        }
        viewModel.exerciseConditionsType.observe(viewLifecycleOwner) {
            val exerciseTypes = resources.getStringArray(R.array.exercise_types_dropdown)
            val itString = exerciseTypes[it.toInt()]
            
            if((binding.exerciseTypeDropDown.text?.toString()?.equals(itString)) != true) {
                binding.exerciseTypeDropDown.setText(itString.toString())
                val adapter = ArrayAdapter(requireContext(), R.layout.item_exercise_type, exerciseTypes)
                binding.exerciseTypeDropDown.setAdapter(adapter)
            }

            when(it) {
                ExerciseConditionsType.TimeIsUp -> {
                    binding.secondsTextInputLayout.visibility = View.VISIBLE
                    binding.countTextInputLayout.visibility = View.GONE
                }
                ExerciseConditionsType.CompleteRepetition -> {
                    binding.secondsTextInputLayout.visibility = View.GONE
                    binding.countTextInputLayout.visibility = View.VISIBLE
                }
            }
        }
        viewModel.repeatTimes.observe(viewLifecycleOwner) {
            if((binding.countTextInputEditText.text?.toString()?.equals(it.toString())) == true)
                return@observe
            
            binding.countTextInputEditText.setText(it.toString())
        }
        viewModel.restSeconds.observe(viewLifecycleOwner) {
            if((binding.restSecondsTextInputEditText.text?.toString()?.equals(it.toString())) == true)
                return@observe
            
            binding.restSecondsTextInputEditText.setText(it.toString())
        }
        viewModel.secondsToDone.observe(viewLifecycleOwner) {
            if((binding.secondsTextInputEditText.text?.toString()?.equals(it.toString())) == true)
                return@observe
            
            binding.secondsTextInputEditText.setText(it.toString())
        }
        
        return binding.root
    }
}