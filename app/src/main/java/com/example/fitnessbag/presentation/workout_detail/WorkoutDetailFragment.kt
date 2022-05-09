package com.example.fitnessbag.presentation.workout_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.FragmentWorkoutDetailBinding
import com.example.fitnessbag.databinding.HeaderExerciseBinding
import com.example.fitnessbag.databinding.ItemImageExerciseBinding
import com.example.fitnessbag.domain.models.Exercise
import com.example.fitnessbag.domain.models.RepeatExercise
import com.example.fitnessbag.domain.models.TimeExercise
import com.example.fitnessbag.domain.models.toDoneToString
import com.example.fitnessbag.presentation.TagsAdapter
import com.example.fitnessbag.presentation.applyTagsStyle
import com.example.fitnessbag.presentation.utils.loadImage
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
        val tagsAdapter = TagsAdapter(true)
        _binding!!.tagsRecyclerView.applyTagsStyle(requireContext())
        _binding!!.tagsRecyclerView.adapter = tagsAdapter
        
        val exerciseAdapter = ExerciseInWorkoutDetailAdapter()
        exerciseAdapter.setHasStableIds(true)
        binding.exercisesRecyclerView.adapter = exerciseAdapter 
        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.exercisesRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        
        binding.exercisesRecyclerView.setHasFixedSize(true)
        binding.exercisesRecyclerView.setItemViewCacheSize(20);
        binding.exercisesRecyclerView.setDrawingCacheEnabled(true);
        binding.exercisesRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        
        binding.backImageView.setOnClickListener {
            findNavController().navigateUp()
        }
        
        viewModel.name.observe(viewLifecycleOwner) {
            _binding!!.titleTextView.text = it

            (activity as AppCompatActivity?)!!.supportActionBar!!.title = it
        }
        viewModel.imageUrl.observe(viewLifecycleOwner) {
            _binding!!.imageView.loadImage(it, R.drawable.no_image_workout)
        }
        viewModel.description.observe(viewLifecycleOwner) {
            _binding!!.descriptionTextView.text = it
            
            if(it.isNullOrBlank()) {
                binding.descriptionTextView.visibility = View.GONE
            }
            else {
                binding.descriptionTextView.visibility = View.VISIBLE
            }
        }
        viewModel.tags.observe(viewLifecycleOwner) {
            if(it.isNullOrEmpty()) {
                binding.tagsLayout.visibility = View.GONE
            }
            else {
                tagsAdapter.updateItems(it)
            }
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
}

class ExerciseInWorkoutDetailAdapter :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val HEADER = 1 
    }
    
    private var exercises: List<Exercise> = listOf()

    fun updateItems(exercises: List<Exercise>) {
        this.exercises = exercises
        this.notifyItemRangeChanged(0, exercises.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if(viewType == HEADER) {
            val view = HeaderExerciseBinding.inflate(inflater, parent, false)
            return HeaderViewHolder(view)
        }
        
        val view = ItemImageExerciseBinding.inflate(inflater, parent, false)
        return ItemImageExerciseViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0)
            return HEADER
        
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder !is ItemImageExerciseViewHolder)
            return

        val exercise = exercises[position - 1]
        holder.itemBinding.titleTextView.text = exercise.name
        holder.itemBinding.exerciseImageView.loadImage(exercise.image, R.drawable.no_image_exercise) 
        holder.itemBinding.toDoneTextView.text = exercise.toDoneToString()
        holder.itemBinding.restSecondsTextView.text = exercise.restSeconds.toString()
    }

    override fun getItemCount(): Int {
        return exercises.size + 1
    }

    class HeaderViewHolder(val itemBinding: HeaderExerciseBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    }

    class ItemImageExerciseViewHolder(val itemBinding: ItemImageExerciseBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    }
}

fun Exercise.toDoneToString() : String
{
    return when(this)
    {
        is RepeatExercise -> "x${this.repeatTimes}"
        is TimeExercise -> this.secondsToDone.toSecondsToDoneString()
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