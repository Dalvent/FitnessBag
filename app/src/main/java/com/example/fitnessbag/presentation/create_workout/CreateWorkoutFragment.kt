package com.example.fitnessbag.presentation.create_workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessbag.MainActivity
import com.example.fitnessbag.databinding.CreateWorkoutFragmentBinding
import com.example.fitnessbag.databinding.LayoutWhatExerciseAddBinding
import com.example.fitnessbag.presentation.applyTagsStyle
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateWorkoutFragment : Fragment() {

    companion object {
        const val SELECTED_EXERCISE = "SELECTED_EXERCISE"
    }
    
    private var addExercisesAdapter: AddExercisesAdapter? = null
    private var editableTagsAdapter: EditableTagsAdapter? = null
    private val viewModel: CreateWorkoutViewModel by viewModel()
    
    private var _binding: CreateWorkoutFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setFragmentResultListener(SELECTED_EXERCISE) { key, bundle ->
            addExercisesAdapter!!.addExercise(bundle.getParcelable(SELECTED_EXERCISE)!!)
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateWorkoutFragmentBinding.inflate(inflater, container, false)
    
        binding.tagsRecyclerView.applyTagsStyle(requireContext())
        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        
        binding.nameTextInputEditText.addTextChangedListener {
            viewModel.setName(it.toString())
        }

        binding.descriptionTextInputEditText.addTextChangedListener {
            viewModel.setDescription(it.toString())
        }
        
        binding.addButton.setOnClickListener { 
            viewModel.save()
            
            findNavController().navigateUp()
        }
        
        viewModel._name.observe(viewLifecycleOwner) {
            if((binding.nameTextInputEditText.text?.toString()?.equals(it.toString())) == true)
                return@observe

            binding.nameTextInputEditText.setText(it)
        }
        
        viewModel._description.observe(viewLifecycleOwner) {
            if((binding.descriptionTextInputEditText.text?.toString()?.equals(it.toString())) == true)
                return@observe

            binding.descriptionTextInputEditText.setText(it)
        }

        viewModel._tags.observe(viewLifecycleOwner) {
            editableTagsAdapter = EditableTagsAdapter(it)
            (activity as MainActivity).addHideKeyboardListener(editableTagsAdapter!!)
            binding.tagsRecyclerView.adapter = editableTagsAdapter
        }

        viewModel._exercises.observe(viewLifecycleOwner) {
            addExercisesAdapter = AddExercisesAdapter(it) {
                showBottomSheetDialog()
            }
            binding.exercisesRecyclerView.adapter = addExercisesAdapter
        }
        
        return binding.root
    }
    
    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val layoutWhatExerciseAdd = LayoutWhatExerciseAddBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(layoutWhatExerciseAdd.root)
        
        layoutWhatExerciseAdd.addExistedLinearLayout.setOnClickListener { 
            findNavController().navigate(CreateWorkoutFragmentDirections.actionCreateWorkoutFragmentToAddExistedExerciseFragment())
            bottomSheetDialog.hide()
        }
        layoutWhatExerciseAdd.addNewLinearLayout.setOnClickListener {
            findNavController().navigate(CreateWorkoutFragmentDirections.actionCreateWorkoutFragmentToAddExerciseFragment())
            bottomSheetDialog.hide()
        }
        bottomSheetDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).removeHideKeyboardListener(editableTagsAdapter!!)
    }
}