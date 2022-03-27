package com.example.fitnessbag.presentation.workouts_catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessbag.databinding.FragmentWorkoutsBinding
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
        binding.recyclerView.adapter = workoutsCatalogAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        
        model.workouts.observe(viewLifecycleOwner) {
            workoutsCatalogAdapter.updateItems(it)
        }
        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}