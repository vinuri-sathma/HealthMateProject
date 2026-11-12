package com.example.healthmate.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.healthmate.R
import com.example.healthmate.controller.RecordController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var recordController: RecordController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recordController = RecordController(this)

        val btnAddRecord = findViewById<Button>(R.id.btnAddRecord)
        val btnViewRecords = findViewById<Button>(R.id.btnViewRecords)

        btnAddRecord.setOnClickListener {
            startActivity(Intent(this, AddRecordActivity::class.java))
        }

        btnViewRecords.setOnClickListener {
            startActivity(Intent(this, ViewRecordsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadDashboardData()
    }

    private fun loadDashboardData() {

        val tvSteps = findViewById<TextView>(R.id.tvDashboardSteps)
        val tvCalories = findViewById<TextView>(R.id.tvDashboardCalories)
        val tvWater = findViewById<TextView>(R.id.tvDashboardWater)

        val today = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(Date())

        // Get dashboard calculations from Controller
        val summary = recordController.getDashboardSummary(today)

        val totalSteps = summary.totalSteps
        val totalCalories = summary.totalCalories
        val totalWater = summary.totalWater
        val waterGoal = summary.waterGoal
        val waterPercentage = summary.waterPercentage

        val tvWaterGoalProgress =
            findViewById<TextView>(R.id.tvWaterGoalProgress)

        val tvWaterGoalPercentage =
            findViewById<TextView>(R.id.tvWaterGoalPercentage)

        val progressWaterGoal =
            findViewById<ProgressBar>(R.id.progressWaterGoal)

        // Update water goal UI
        tvWaterGoalProgress.text =
            getString(R.string.water_goal_progress, totalWater, waterGoal)

        tvWaterGoalPercentage.text =
            getString(R.string.water_goal_percentage, waterPercentage)

        progressWaterGoal.progress = waterPercentage

        // Update dashboard UI
        tvSteps.text = getString(R.string.dashboard_steps, totalSteps)

        tvCalories.text = getString(
            R.string.dashboard_calories,
            totalCalories.toString()
        )

        tvWater.text = getString(R.string.dashboard_water, totalWater)
    }
}
