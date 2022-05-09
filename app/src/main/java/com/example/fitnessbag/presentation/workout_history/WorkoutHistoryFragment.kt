package com.example.fitnessbag.presentation.workout_history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.WorkoutHistoryFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WorkoutHistoryFragment : Fragment() {
    
    private val viewModel: WorkoutHistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = WorkoutHistoryFragmentBinding.inflate(inflater, container, false)
        
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        binding.historyRecyclerView.addItemDecoration(dividerItemDecoration)
        
        viewModel.downedWorkouts.observe(viewLifecycleOwner) {
            binding.historyRecyclerView.adapter = WorkoutHistoryAdapter(it)
            
            if(it.isNullOrEmpty()) {
                binding.historyRecyclerView.visibility = View.GONE
                binding.noItemsLayout.visibility = View.VISIBLE
            }
            else {
                binding.historyRecyclerView.visibility = View.VISIBLE
                binding.noItemsLayout.visibility = View.GONE
            }
        }
        
        return binding.root
    }
}