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
import com.example.fitnessbag.presentation.create_workout.CreateWorkoutFragmentDirections
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

            if(model.filterString.value != null && model.filterString.value != "")
                workoutsCatalogAdapter?.setTextFilter(model.filterString.value!!)
        }
        
        model.filterString.observe(viewLifecycleOwner) {
            workoutsCatalogAdapter?.setTextFilter(it)
        }

        binding.fab.setOnClickListener {
            val action =
                WorkoutsCatalogFragmentDirections.actionWorkoutsCatalogFragmentToCreateWorkoutFragment()
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

        layoutWhatExerciseAdd.deleteLinearLayout.setOnClickListener {
            workoutsCatalogAdapter!!.removeWithViewModel(this.model, workout)
            bottomSheetDialog.hide()
        }
        bottomSheetDialog.show()
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_search, menu)

        val menuItem = menu.findItem(R.id.search_item)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Поиск по упражнениям..."
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(var1: String?): Boolean {
                model.updateFilterString(var1!!)
                return true
            }

            override fun onQueryTextChange(var1: String?): Boolean {
                model.updateFilterString(var1!!)
                return true
            }
        })
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