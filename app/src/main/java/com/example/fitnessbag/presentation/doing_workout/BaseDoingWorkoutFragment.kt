package com.example.fitnessbag.presentation.doing_workout

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitnessbag.presentation.CustomBackPressed
import com.example.fitnessbag.presentation.done_workout.DoneWorkoutFragmentDirections

abstract class BaseDoingWorkoutFragment : Fragment(), CustomBackPressed {
    override fun onStart() {
        super.onStart()

        getActionBar()?.hide()
    }

    override fun onStop() {
        super.onStop()

        getActionBar()?.show()
    }

    override fun onBackPressed(): Boolean {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure want to leave?")
        builder.setPositiveButton("Yes",) { var1, var2 ->
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") { var1, var2 ->
        }
        val alertDialog = builder.create()
        alertDialog.show()

        return false
    }

    private fun getActionBar() = getCompatActivity().supportActionBar

    private fun getCompatActivity() = (activity as AppCompatActivity)
}