package com.example.healthmate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

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

        val latestRecord = databaseHelper.getLatestRecord()


        if (latestRecord != null) {
            tvSteps.text = getString(R.string.dashboard_steps, latestRecord.steps)
            tvCalories.text = getString(R.string.dashboard_calories, latestRecord.calories.toString())
            tvWater.text = getString(R.string.dashboard_water, latestRecord.water)
        } else {
            tvSteps.text = getString(R.string.dashboard_steps, 0)
            tvCalories.text = getString(R.string.dashboard_calories, "0")
            tvWater.text = getString(R.string.dashboard_water, 0)
        }

    }
}
