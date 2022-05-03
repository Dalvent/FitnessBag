package com.example.fitnessbag.presentation.create_workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessbag.MainActivity
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.CreateWorkoutFragmentBinding
import com.example.fitnessbag.databinding.LayoutWhatExerciseAddBinding
import com.example.fitnessbag.presentation.applyTagsStyle
import com.google.android.material.bottomsheet.BottomSheetDialog


class CreateWorkoutFragment : Fragment() {

    private var editableTagsAdapter: EditableTagsAdapter? = null
    private lateinit var viewModel: CreateWorkoutViewModel
    
    private var _binding: CreateWorkoutFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateWorkoutFragmentBinding.inflate(inflater, container, false)
    
        binding.tagsRecyclerView.applyTagsStyle(requireContext())
        editableTagsAdapter = EditableTagsAdapter(listOf())
        (activity as MainActivity).addHideKeyboardListener(editableTagsAdapter!!)
        binding.tagsRecyclerView.adapter = editableTagsAdapter
        
        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.exercisesRecyclerView.adapter = AddExercisesAdapter(listOf()) {
            showBottomSheetDialog()
        }
        
        return binding.root
    }
    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val layoutWhatExerciseAdd = LayoutWhatExerciseAddBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(layoutWhatExerciseAdd.root)
        layoutWhatExerciseAdd.addExistedLinearLayout.setOnClickListener { 
            
        }
        layoutWhatExerciseAdd.addNewLinearLayout.setOnClickListener {
            
        }
        bottomSheetDialog.show()
    }
    

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateWorkoutViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).removeHideKeyboardListener(editableTagsAdapter!!)
    }
}