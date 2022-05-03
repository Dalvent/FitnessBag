package com.example.fitnessbag.presentation.workout_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.R
import com.example.fitnessbag.data.models.ExerciseExecutionConditionsType.*
import com.example.fitnessbag.data.models.ExerciseModel
import com.example.fitnessbag.databinding.FragmentWorkoutDetailBinding
import com.example.fitnessbag.databinding.ItemWorkoutInWorkoutDetailBinding
import com.example.fitnessbag.presentation.TagsAdapter
import com.example.fitnessbag.presentation.applyTagsStyle
import org.koin.androidx.viewmodel.ext.android.viewModel

class WorkoutDetailFragment : Fragment() {
    private val args: WorkoutDetailFragmentArgs by navArgs()
    private val viewModel: WorkoutDetailViewModel by viewModel()

    private var _binding: FragmentWorkoutDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutDetailBinding.inflate(inflater, container, false)
        
        viewModel.initialize(args.workoutId)
        val tagsAdapter = TagsAdapter()
        _binding!!.tagsRecyclerView.applyTagsStyle(requireContext())
        _binding!!.tagsRecyclerView.adapter = tagsAdapter
        
        
        val exerciseAdapter = ExerciseInWorkoutDetailAdapter()
        binding.exercisesRecyclerView.adapter = exerciseAdapter 
        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.exercisesRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        
        viewModel.name.observe(viewLifecycleOwner) {
            _binding!!.titleTextView.text = it
        }
        viewModel.description.observe(viewLifecycleOwner) {
            _binding!!.descriptionTextView.text = it
        }
        viewModel.tags.observe(viewLifecycleOwner) {
            tagsAdapter.updateItems(it)
        }
        viewModel.exercise.observe(viewLifecycleOwner) {
            exerciseAdapter.updateItems(it)
        }
        
        binding.startButton.setOnClickListener {
            val action = WorkoutDetailFragmentDirections.actionWorkoutDetailFragmentToDoingWorkoutFragment(viewModel.workoutModel)
            findNavController().navigate(action)
        }
        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ExerciseInWorkoutDetailAdapter :  RecyclerView.Adapter<ExerciseInWorkoutDetailAdapter.ExerciseInWorkoutDetailViewHolder>() {

    private var exercises: List<ExerciseModel> = listOf()

    fun updateItems(exercises: List<ExerciseModel>) {
        this.exercises = exercises
        this.notifyItemRangeChanged(0, exercises.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseInWorkoutDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemWorkoutInWorkoutDetailBinding.inflate(inflater)
        return ExerciseInWorkoutDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseInWorkoutDetailViewHolder, position: Int) {
        holder.itemBinding.titleTextView.text = exercises[position].name
        holder.itemBinding.countTextView.text = exercises[position].toDoneToString()
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    class ExerciseInWorkoutDetailViewHolder(val itemBinding: ItemWorkoutInWorkoutDetailBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    }
}

fun ExerciseModel.toDoneToString() : String
{
    return when(this.executionConditionsType)
    {
        TimeIsUp -> this.secondsToDone.toSecondsToDoneString()
        CompleteRepetition -> "x${this.repeatTimes}"
    } 
}

fun Int.toSecondsToDoneString() : String {
    val seconds = this % 60
    val minutes = (this - seconds) / 60
    return "${minutes.toTwoDigitFormat()}:${seconds.toTwoDigitFormat()}"
}

fun Int.toTwoDigitFormat() : String {
    var intString = this.toString()
    if(intString.length == 1)
        intString = "0" + intString
    return intString
}