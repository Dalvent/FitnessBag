package com.example.fitnessbag.presentation.workouts_catalog

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.FragmentWorkoutsBinding
import com.example.fitnessbag.databinding.LayoutWhatWorkoutMoreBinding
import com.example.fitnessbag.domain.models.Workout
import com.example.fitnessbag.presentation.execute
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class WorkoutsCatalogFragment : Fragment() {

    private var workoutsCatalogAdapter: WorkoutsCatalogAdapter? = null
    private val model: WorkoutCatalogViewModel by viewModel()

    private var _binding: FragmentWorkoutsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutsBinding.inflate(inflater, container, false)

        model.catalog.observe(viewLifecycleOwner) {
            workoutsCatalogAdapter = WorkoutsCatalogAdapter(it,
                { workout -> openWorkoutDetail(workout) },
                { workout -> openWorkoutBottomSheetDialog(workout) })
            binding.recyclerView.adapter = workoutsCatalogAdapter
            binding.recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            binding.noItemsLayout.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE

            if (model.filterString.value != null && model.filterString.value != "")
                workoutsCatalogAdapter?.setTextFilter(model.filterString.value!!)
        }

        model.filterString.observe(viewLifecycleOwner) {
            workoutsCatalogAdapter?.setTextFilter(it)
        }

        binding.fab.setOnClickListener {
            val action =
                WorkoutsCatalogFragmentDirections.actionWorkoutsCatalogFragmentToCreateWorkoutFragment(
                    -1
                )
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun openWorkoutDetail(workout: Workout) {
        val action =
            WorkoutsCatalogFragmentDirections.actionWorkoutsCatalogFragmentToWorkoutDetailFragment(
                workout.id!!
            )
        findNavController().navigate(action)
    }

    private fun openWorkoutBottomSheetDialog(workout: Workout) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val layoutWhatExerciseAdd = LayoutWhatWorkoutMoreBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(layoutWhatExerciseAdd.root)

        layoutWhatExerciseAdd.shareLinearLayout.setOnClickListener {
            
        }
        layoutWhatExerciseAdd.deleteLinearLayout.setOnClickListener {
            workoutsCatalogAdapter!!.removeWithViewModel(this.model, workout)
            bottomSheetDialog.hide()
        }
        layoutWhatExerciseAdd.copyLinearLayout.setOnClickListener {
            val action =
                WorkoutsCatalogFragmentDirections.actionWorkoutsCatalogFragmentToCreateWorkoutFragment(
                    workout.id!!
                )
            findNavController().navigate(action)
            bottomSheetDialog.hide()
        }
        bottomSheetDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_history_settings, menu)

        val menuItem = menu.findItem(R.id.search_item)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = requireContext().getString(R.string.search_dots)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(var1: String?): Boolean {
                model.updateFilterString(var1!!)
                return true
            }

            override fun onQueryTextChange(var1: String?): Boolean {
                model.updateFilterString(var1!!)
                return true
            }
        })
        val historyItem = menu.findItem(R.id.history_item)
        historyItem.setOnMenuItemClickListener {
            val action = WorkoutsCatalogFragmentDirections.actionWorkoutsCatalogFragmentToWorkoutHistoryFragment()
            findNavController().navigate(action)
            return@setOnMenuItemClickListener true
        }

        val settingsItem = menu.findItem(R.id.settings_item)
        settingsItem.setOnMenuItemClickListener {
            WorkoutsCatalogFragmentDirections.actionWorkoutsCatalogFragmentToSettingsFragment().execute(findNavController())
            return@setOnMenuItemClickListener true
        }
        
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onStart() {
        model.update()
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}