package com.example.fitnessbag.presentation.create_workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessbag.MainActivity
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.CreateWorkoutFragmentBinding
import com.example.fitnessbag.databinding.LayoutWhatExerciseAddBinding
import com.example.fitnessbag.presentation.applyTagsStyle
import com.example.fitnessbag.presentation.utils.loadImage
import com.example.fitnessbag.presentation.workout_detail.WorkoutDetailFragmentArgs
import com.example.fitnessbag.utils.ValidationException
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateWorkoutFragment : Fragment() {

    companion object {
        const val SELECTED_EXERCISE = "SELECTED_EXERCISE"
    }
    
    private val args: CreateWorkoutFragmentArgs by navArgs()

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
        if(args.workoutId != (-1).toLong()) {
            viewModel.loadTemplate(args.workoutId)
        }
        
        _binding = CreateWorkoutFragmentBinding.inflate(inflater, container, false)

        binding.tagsRecyclerView.applyTagsStyle(requireContext())
        binding.exercisesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        
        binding.exercisesRecyclerView.setHasFixedSize(true)
        binding.exercisesRecyclerView.setItemViewCacheSize(20);
        binding.exercisesRecyclerView.setDrawingCacheEnabled(true);
        binding.exercisesRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        binding.nameTextInputEditText.addTextChangedListener {
            viewModel.setName(it.toString())
        }

        binding.descriptionTextInputEditText.addTextChangedListener {
            viewModel.setDescription(it.toString())
        }

        binding.addButton.setOnClickListener {
            try {
                viewModel.save()
            } catch (ex: ValidationException) {
                Toast.makeText(requireContext(), ex.reason, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            findNavController().navigateUp()
        }

        binding.loadImageButton.setOnClickListener {
            viewModel.imagePickerService.pickImage { uri ->
                viewModel.setUrl(uri.toString())
            }
        }
        
        binding.resetImageButton.setOnClickListener {
            viewModel.setUrl("")
        }
        
        viewModel.pathUrl.observe(viewLifecycleOwner) {
            binding.exerciseImageView.loadImage(it, R.drawable.no_image_workout)
            binding.resetImageButton.isEnabled = it != ""
        }


        viewModel._name.observe(viewLifecycleOwner) {
            if ((binding.nameTextInputEditText.text?.toString()?.equals(it.toString())) == true)
                return@observe

            binding.nameTextInputEditText.setText(it)
        }

        viewModel._description.observe(viewLifecycleOwner) {
            if ((binding.descriptionTextInputEditText.text?.toString()
                    ?.equals(it.toString())) == true
            )
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