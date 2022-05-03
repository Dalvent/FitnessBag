package com.example.fitnessbag.presentation.create_workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.AddExerciseFragmentBinding


class AddExerciseFragment : Fragment() {

    private var _binding: AddExerciseFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddExerciseFragmentBinding.inflate(inflater, container, false)

        val exerciseTypes = resources.getStringArray(R.array.exercise_types_dropdown)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_exercise_type, exerciseTypes)
        binding.exerciseTypeDropDown.setAdapter(adapter)

        return binding.root
    }
}