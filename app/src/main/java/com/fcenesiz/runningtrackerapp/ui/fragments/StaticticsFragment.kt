package com.fcenesiz.runningtrackerapp.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fcenesiz.runningtrackerapp.R
import com.fcenesiz.runningtrackerapp.ui.viewmodels.MainViewModel
import com.fcenesiz.runningtrackerapp.ui.viewmodels.StaticticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaticticsFragment : Fragment(R.layout.fragment_statistics) {
    private val viewModel: StaticticsViewModel by viewModels()
}