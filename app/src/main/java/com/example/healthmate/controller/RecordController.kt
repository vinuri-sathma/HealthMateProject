package com.example.healthmate.controller

import android.content.Context
import com.example.healthmate.DatabaseHelper
import com.example.healthmate.HealthRecord
import com.example.healthmate.DashboardSummary

class RecordController(context: Context) {

    private val databaseHelper = DatabaseHelper(context)

    // Validate health record values
    fun isValidRecord(
        steps: Int?,
        calories: Int?,
        water: Int?
    ): Boolean {
        return steps != null && steps >= 0 &&
                calories != null && calories >= 0 &&
                water != null && water >= 0
    }

    // Add a new health record
    fun addRecord(
        steps: Int,
        calories: Int,
        water: Int,
        date: String
    ): Boolean {
        return databaseHelper.insertRecord(
            steps, calories, water, date
        )
    }

    // Get all health records
    fun getAllRecords(): List<HealthRecord> {
        return databaseHelper.getAllRecords()
    }

    // Get health records by selected date
    fun getRecordsByDate(date: String): List<HealthRecord> {

        val records = databaseHelper.getAllRecords()

        return if (date.isEmpty()) {
            records
        } else {
            records.filter { it.date == date }
        }
    }

    // Update an existing health record
    fun updateRecord(
        recordId: Int,
        steps: Int,
        calories: Int,
        water: Int
    ): Boolean {
        return databaseHelper.updateRecord(
            recordId, steps, calories, water
        )
    }

    // Delete a health record
    fun deleteRecord(recordId: Int): Boolean {
        return databaseHelper.deleteRecord(recordId)
    }

    // Calculate dashboard summary
    fun getDashboardSummary(date: String): DashboardSummary {

        val todayRecords = databaseHelper.getAllRecords()
            .filter { it.date == date }

        val totalSteps = todayRecords.sumOf { it.steps }
        val totalCalories = todayRecords.sumOf { it.calories }
        val totalWater = todayRecords.sumOf { it.water }

        val waterGoal = 2000

        val waterPercentage =
            ((totalWater.toDouble() / waterGoal) * 100)
                .toInt()
                .coerceIn(0, 100)

        return DashboardSummary(
            totalSteps,
            totalCalories,
            totalWater,
            waterGoal,
            waterPercentage
        )
    }
}
