package com.example.fitnessbag.presentation.settings

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.fitnessbag.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : PreferenceFragmentCompat() {

    val viewModel: SettingsViewModel by viewModel()
    
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val button: Preference = findPreference(getString(R.string.clear_data))!!
        button.setOnPreferenceClickListener {
            viewModel.clearData()
            return@setOnPreferenceClickListener true
        }
    }
}