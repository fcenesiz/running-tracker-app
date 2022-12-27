package com.fcenesiz.runningtrackerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fcenesiz.runningtrackerapp.R
import com.fcenesiz.runningtrackerapp.databinding.FragmentSetupBinding
import dagger.hilt.android.AndroidEntryPoint


class SetupFragment : Fragment(R.layout.fragment_setup) {

    lateinit var binding: FragmentSetupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetupBinding.inflate(layoutInflater)
        val view = binding.root
        binding.apply {
            tvContinue.setOnClickListener {
                println("clicked")
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            }
        }
        return view
    }


}