package com.example.fitnessbag.presentation.workout_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessbag.data.repositories.WorkoutDetailRepository
import com.example.fitnessbag.databinding.FragmentWorkoutDetailBinding
import com.example.fitnessbag.databinding.FragmentWorkoutsBinding
import com.example.fitnessbag.presentation.useForTags
import com.example.fitnessbag.presentation.workouts_catalog.WorkoutCatalogViewModel
import com.example.fitnessbag.presentation.workouts_catalog.WorkoutsCatalogAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class WorkoutDetailFragment : Fragment() {
    private val model: WorkoutDetailViewModel by viewModel()

    private var _binding: FragmentWorkoutDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutDetailBinding.inflate(inflater, container, false)
        
        model.initialize(31)
        val tagsAdapter = _binding!!.tagsRecyclerView.useForTags(requireContext())
        
        model.name.observe(viewLifecycleOwner) {
            _binding!!.titleTextView.text = it
        }
        model.description.observe(viewLifecycleOwner) {
            _binding!!.titleTextView.text = it
        }
        model.tags.observe(viewLifecycleOwner) {
            tagsAdapter.updateItems(it)
        }
        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}