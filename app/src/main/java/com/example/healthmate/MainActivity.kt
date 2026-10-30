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
            tvSteps.text = "👟  Steps\n\n${latestRecord.steps} steps"
            tvCalories.text = "🔥  Calories\n\n${latestRecord.calories} kcal"
            tvWater.text = "💧  Water Intake\n\n${latestRecord.water} ml"
        } else {
            tvSteps.text = "👟  Steps\n\n0 steps"
            tvCalories.text = "🔥  Calories\n\n0 kcal"
            tvWater.text = "💧  Water Intake\n\n0 ml"
        }
    }
}
