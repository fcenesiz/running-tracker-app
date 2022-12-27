package com.fcenesiz.runningtrackerapp.repositories

import com.fcenesiz.runningtrackerapp.db.Run
import com.fcenesiz.runningtrackerapp.db.RunDAO
import javax.inject.Inject

// don't need to set providers for this class on AppModule, because runDAO object provided
// so dagger-hilt knows also how to create MainRepository
class MainRepository @Inject constructor(
    val runDAO: RunDAO
) {
    suspend fun insert(run: Run) = runDAO.insert(run)

    suspend fun delete(run: Run) = runDAO.delete(run)

    // these are not suspend because of being suspend livedata by default

    fun getAllRunsSortedByDate() = runDAO.getAllRunsSortedByDate()

    fun getAllRunsSortedByTimeInMillis() = runDAO.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByCaloriesBurned() = runDAO.getAllRunsSortedByCaloriesBurned()

    fun getAllRunsSortedByAvgSpeed() = runDAO.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByDistance() = runDAO.getAllRunsSortedByDistance()

    fun getTotalTimeInMillis() = runDAO.getTotalTimeInMillis()

    fun getTotalCaloriesBurned() = runDAO.getTotalCaloriesBurned()

    fun getTotalAvgSpeed() = runDAO.getTotalAvgSpeed()
}