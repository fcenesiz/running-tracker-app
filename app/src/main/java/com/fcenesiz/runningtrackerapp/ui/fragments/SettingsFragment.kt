package com.fcenesiz.runningtrackerapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fcenesiz.runningtrackerapp.R
import com.fcenesiz.runningtrackerapp.databinding.FragmentSettingsBinding
import com.fcenesiz.runningtrackerapp.other.Constants.KEY_NAME
import com.fcenesiz.runningtrackerapp.other.Constants.KEY_WEIGHT
import com.fcenesiz.runningtrackerapp.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFieldsFromSharedPreferences()
        binding.apply {
            btnApplyChanges.setOnClickListener {
                val success = applyChangesToSharedPreferences()
                if (success)
                    Snackbar.make(view, "Saved changes", Snackbar.LENGTH_LONG).show()
                else
                    Snackbar.make(view, "Please fill out all the fields", Snackbar.LENGTH_LONG)
                        .show()
            }
        }
    }

    private fun loadFieldsFromSharedPreferences() {
        binding.apply {
            val name = sharedPreferences.getString(KEY_NAME, "")
            val weight = sharedPreferences.getFloat(KEY_WEIGHT, 80f)
            etName.setText(name)
            etWeight.setText(weight.toString())
        }
    }

    private fun applyChangesToSharedPreferences(): Boolean {
        binding.apply {
            val nameText = etName.text.toString()
            val weightText = etWeight.text.toString()
            if (nameText.isEmpty() || weightText.isEmpty())
                return false
            sharedPreferences.edit()
                .putString(KEY_NAME, nameText)
                .putFloat(KEY_WEIGHT, weightText.toFloat())
                .apply()
            val toolbarText = "Let's go $nameText"
            (requireActivity() as MainActivity).binding.tvToolbarTitle.text = toolbarText
            return true
        }
    }

}