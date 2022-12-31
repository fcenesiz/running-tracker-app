package com.fcenesiz.runningtrackerapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.fcenesiz.runningtrackerapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StaticticsViewModel @Inject constructor(
    val mainRepository: MainRepository
) :ViewModel(){

    val totalTimeRun = mainRepository.getTotalTimeInMillis()
    val totalDistance = mainRepository.getTotalDistance()
    val totalCaloriesBurned = mainRepository.getTotalCaloriesBurned()
    val totalAvgSpeed = mainRepository.getTotalAvgSpeed()

    val runsSortedByDate = mainRepository.getAllRunsSortedByDate()

}