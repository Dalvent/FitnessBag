package com.example.fitnessbag.presentation.done_workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.fitnessbag.R
import com.example.fitnessbag.databinding.DoneWorkoutFragmentBinding
import com.example.fitnessbag.presentation.CustomBackPressed

class DoneWorkoutFragment : Fragment(), CustomBackPressed {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DoneWorkoutFragmentBinding.inflate(inflater, container, false)
        
        binding.backImageView.setOnClickListener {
            val action = DoneWorkoutFragmentDirections.actionDoneWorkoutFragmentToWorkoutsCatalogFragment()
            findNavController().navigate(action)
        }
        
        return binding.root
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
    override fun onBackPressed(): Boolean {
        val action = DoneWorkoutFragmentDirections.actionDoneWorkoutFragmentToWorkoutsCatalogFragment()
        findNavController().navigate(action)
        
        return false
    }
}