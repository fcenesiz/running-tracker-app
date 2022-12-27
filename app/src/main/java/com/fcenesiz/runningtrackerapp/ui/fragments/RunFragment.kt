package com.fcenesiz.runningtrackerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fcenesiz.runningtrackerapp.R
import com.fcenesiz.runningtrackerapp.databinding.FragmentRunBinding
import com.fcenesiz.runningtrackerapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run) {

    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: FragmentRunBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRunBinding.inflate(layoutInflater)
        val view = binding.root
        binding.apply {
            fab.setOnClickListener {
                findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
            }
        }
        return view
    }
}