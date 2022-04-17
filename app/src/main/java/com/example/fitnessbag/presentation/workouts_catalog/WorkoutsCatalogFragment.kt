package com.example.fitnessbag.presentation.workouts_catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.FragmentWorkoutsBinding
import com.example.fitnessbag.presentation.navigateTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class WorkoutsCatalogFragment : Fragment() {

    private val model: WorkoutCatalogViewModel by viewModel()
    
    private var _binding: FragmentWorkoutsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutsBinding.inflate(inflater, container, false)

        val workoutsCatalogAdapter = WorkoutsCatalogAdapter()
        workoutsCatalogAdapter.setItemClickListener {
            findNavController().navigate(R.id.action_workout_to_workout_detail)
        }
        binding.recyclerView.adapter = workoutsCatalogAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        
        model.workouts.observe(viewLifecycleOwner) {
            workoutsCatalogAdapter.updateItems(it)
        }
        
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_StartFragment_to_createWorkoutFragment)
        }
        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}