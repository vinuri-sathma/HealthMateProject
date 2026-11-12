package com.example.healthmate.controller

import android.content.Context
import com.example.healthmate.DatabaseHelper
import com.example.healthmate.HealthRecord

class RecordController(context: Context) {

    private val databaseHelper = DatabaseHelper(context)

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
}
