package com.example.fitnessbag.presentation.add_existed_exercise

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.AddExistedExerciseFragmentBinding
import com.example.fitnessbag.presentation.create_workout.CreateWorkoutFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddExistedExerciseFragment : Fragment() {

    private var filteredExistedExerciseAdapter: FilteredExistedExerciseAdapter? = null
    
    private val viewModel: AddExistedExerciseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AddExistedExerciseFragmentBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.setHasFixedSize(true)
        
        viewModel.exercises.observe(viewLifecycleOwner) {
            filteredExistedExerciseAdapter = FilteredExistedExerciseAdapter(it) {
                setFragmentResult(CreateWorkoutFragment.SELECTED_EXERCISE, Bundle().apply { 
                    this.putParcelable(CreateWorkoutFragment.SELECTED_EXERCISE, it)
                })
                findNavController().popBackStack()
            }
            binding.recyclerView.adapter = filteredExistedExerciseAdapter
        }
        
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        
        inflater.inflate(R.menu.menu_search, menu)
        
        val menuItem = menu.findItem(R.id.search_item)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = requireContext().getString(R.string.search_dots)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(var1: String?): Boolean {
                filteredExistedExerciseAdapter?.setTextFilter(var1!!)
                return true
            }

            override fun onQueryTextChange(var1: String?): Boolean {
                filteredExistedExerciseAdapter?.setTextFilter(var1!!)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}