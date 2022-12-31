package com.fcenesiz.runningtrackerapp.other

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import com.fcenesiz.runningtrackerapp.R
import com.fcenesiz.runningtrackerapp.databinding.MarkerViewBinding
import com.fcenesiz.runningtrackerapp.db.Run
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(
    val runs: List<Run>,
    c: Context,
    layoutId: Int
) : MarkerView(c, layoutId) {

    val tvDate :TextView
    val tvAvgSpeed :TextView
    val tvDistance :TextView
    val tvDuration :TextView
    val tvCaloriesBurned :TextView

    init {
        tvDate = findViewById(R.id.tvDate)
        tvAvgSpeed = findViewById(R.id.tvAvgSpeed)
        tvDistance = findViewById(R.id.tvDistance)
        tvDuration = findViewById(R.id.tvDuration)
        tvCaloriesBurned = findViewById(R.id.tvCaloriesBurned)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)

        if (e == null)
            return
        val currentRunId = e.x.toInt()
        val run = runs[currentRunId]

        val calender = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }




        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        tvDate.text = dateFormat.format(calender.time)

        val avgSpeed = "${run.avgSpeedInKMH}km/h"
        tvAvgSpeed.text = avgSpeed

        val distanceInKm = "${run.distanceInMeters / 1000f}km"
        tvDistance.text = distanceInKm

        tvDuration.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

        val caloriesBurned = "${run.caloriesBurned}kcal"
        tvCaloriesBurned.text = caloriesBurned

    }
}