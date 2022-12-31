package com.fcenesiz.runningtrackerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fcenesiz.runningtrackerapp.R
import com.fcenesiz.runningtrackerapp.databinding.FragmentStatisticsBinding
import com.fcenesiz.runningtrackerapp.other.TrackingUtility
import com.fcenesiz.runningtrackerapp.ui.viewmodels.MainViewModel
import com.fcenesiz.runningtrackerapp.ui.viewmodels.StaticticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StaticticsFragment : Fragment(R.layout.fragment_statistics) {

    lateinit var binding: FragmentStatisticsBinding
    private val viewModel: StaticticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
    }

    private fun subscribeToObservers(){
        binding.apply {
            viewModel.apply {
                totalTimeRun.observe(viewLifecycleOwner){
                    it?.let {
                        val totalTimeRun = TrackingUtility.getFormattedStopWatchTime(it)
                        tvTotalTime.text = totalTimeRun
                    }
                }
                totalDistance.observe(viewLifecycleOwner){
                    it?.let {
                        val km = it / 1000f
                        val totalDistance = round(km * 10f) / 10f
                        val  totalDistanceString = "${totalDistance}km"
                        tvTotalDistance.text = totalDistanceString
                    }
                }
                totalAvgSpeed.observe(viewLifecycleOwner){
                    it?.let {
                        val avgSpeed = round(it * 10f) / 10f
                        val avgSpeedString = "${avgSpeed}km/h"
                        tvAverageSpeed.text = avgSpeedString
                    }
                }
                totalCaloriesBurned.observe(viewLifecycleOwner){
                    it?.let {
                        val totalCalories = "${it}kcal"
                        tvTotalCalories.text = totalCalories
                    }
                }
            }
        }
    }
}