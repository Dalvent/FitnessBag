package com.example.fitnessbag.presentation.create_exersice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.fitnessbag.R
import com.example.fitnessbag.data.models.ExerciseExecutionConditionsType
import com.example.fitnessbag.databinding.CreateExerciseFragmentBinding
import com.example.fitnessbag.utils.ValidationException
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception


class CreateExerciseFragment : Fragment() {

    private var _binding: CreateExerciseFragmentBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: CreateExerciseViewModel by viewModel()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateExerciseFragmentBinding.inflate(inflater, container, false)
        
        binding.nameTextInputEditText.addTextChangedListener {
            viewModel.setName(it.toString())
        }
        binding.descriptionTextInputEditText.addTextChangedListener {
            viewModel.setDescription(it.toString())
        }
        binding.countTextInputEditText.addTextChangedListener {
            val value = it?.toString()?.toInt()
            viewModel.setRepeatTimes(value!!)
        }
        binding.secondsTextInputEditText.addTextChangedListener {
            val value = it?.toString()?.toInt()
            viewModel.setSecondsToDone(value!!)
        }
        binding.restSecondsTextInputEditText.addTextChangedListener {
            val value = it?.toString()?.toInt()
            viewModel.setRestSeconds(value!!)
        }
        binding.exerciseTypeDropDown.addTextChangedListener {
            viewModel.setExerciseExecutionConditionsType(convertToConditionsType(it.toString()))
        }
        binding.addButton.setOnClickListener {
            try {
                viewModel.save()
            }
            catch (ex: ValidationException) {
                Toast.makeText(requireContext(), "Fill all fields!!!", Toast.LENGTH_SHORT).show()
            }
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
        viewModel.exerciseExecutionConditionsType.observe(viewLifecycleOwner) {
            val exerciseTypes = resources.getStringArray(R.array.exercise_types_dropdown)
            val itString = exerciseTypes[toInt(it)]
            
            if((binding.exerciseTypeDropDown.text?.toString()?.equals(itString)) != true) {
                binding.exerciseTypeDropDown.setText(itString.toString())
                val adapter = ArrayAdapter(requireContext(), R.layout.item_exercise_type, exerciseTypes)
                binding.exerciseTypeDropDown.setAdapter(adapter)
            }

            when(it) {
                ExerciseExecutionConditionsType.TimeIsUp -> {
                    binding.secondsTextInputLayout.visibility = View.VISIBLE
                    binding.countTextInputLayout.visibility = View.GONE
                }
                ExerciseExecutionConditionsType.CompleteRepetition -> {
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
    
    private fun convertToConditionsType(it: String): ExerciseExecutionConditionsType {
        return when(it) {
            "By time" -> ExerciseExecutionConditionsType.TimeIsUp
            "By repetition" -> ExerciseExecutionConditionsType.CompleteRepetition
            else -> throw IllegalArgumentException()
        }
    }
    
    private fun toInt(it: ExerciseExecutionConditionsType): Int {
        return when(it) {
            ExerciseExecutionConditionsType.TimeIsUp -> 0
            ExerciseExecutionConditionsType.CompleteRepetition -> 1
        }
    }
}