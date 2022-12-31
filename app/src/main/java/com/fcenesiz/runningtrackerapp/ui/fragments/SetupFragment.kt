package com.fcenesiz.runningtrackerapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.fcenesiz.runningtrackerapp.R
import com.fcenesiz.runningtrackerapp.databinding.FragmentSetupBinding
import com.fcenesiz.runningtrackerapp.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.fcenesiz.runningtrackerapp.other.Constants.KEY_NAME
import com.fcenesiz.runningtrackerapp.other.Constants.KEY_WEIGHT
import com.fcenesiz.runningtrackerapp.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @set:Inject
    var isFirstAppOpen = true

    lateinit var binding: FragmentSetupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetupBinding.inflate(layoutInflater)
        val view = binding.root
        binding.apply {
            if (!isFirstAppOpen){
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.setupFragment, true)
                    .build()
                findNavController().navigate(
                    R.id.action_setupFragment_to_runFragment,
                    savedInstanceState,
                    navOptions
                )
            }
            tvContinue.setOnClickListener {
                val success = writePersonalDataToSharedPreferences()
                if (success)
                    findNavController().navigate(R.id.action_setupFragment_to_runFragment)
                else
                    Snackbar.make(
                        requireView(),
                        "Please enter all the fields",
                        Snackbar.LENGTH_LONG
                    ).show()
            }
        }
        return view
    }

    private fun writePersonalDataToSharedPreferences(): Boolean {
        binding.apply {
            val name = etName.text.toString()
            val weight = etWeight.text.toString()
            if (name.isEmpty() || weight.isEmpty()) {
                return false
            }
            sharedPreferences.edit()
                .putString(KEY_NAME, name)
                .putFloat(KEY_WEIGHT, weight.toFloat())
                .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
                .apply()
            val toolbarText = "Let's go, $name!"
            (requireActivity() as MainActivity)
                .binding.tvToolbarTitle.text = toolbarText
            return true
        }
    }

}