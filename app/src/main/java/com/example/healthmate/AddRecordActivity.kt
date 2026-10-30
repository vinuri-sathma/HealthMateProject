package com.example.healthmate

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddRecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)

        val etSteps = findViewById<EditText>(R.id.etSteps)
        val etCalories = findViewById<EditText>(R.id.etCalories)
        val etWater = findViewById<EditText>(R.id.etWater)
        val btnSave = findViewById<Button>(R.id.btnSaveRecord)

        val databaseHelper = DatabaseHelper(this)

        btnSave.setOnClickListener {

            val steps = etSteps.text.toString().trim()
            val calories = etCalories.text.toString().trim()
            val water = etWater.text.toString().trim()

            if (steps.isEmpty() || calories.isEmpty() || water.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val stepsValue = steps.toIntOrNull()
            val caloriesValue = calories.toDoubleOrNull()
            val waterValue = water.toIntOrNull()

            if (stepsValue == null || caloriesValue == null || waterValue == null ||
                stepsValue < 0 || !caloriesValue.isFinite() ||
                caloriesValue < 0 || waterValue < 0) {

                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val date = SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault()
            ).format(Date())

            val isSaved = databaseHelper.insertRecord(
                stepsValue,
                caloriesValue,
                waterValue,
                date
            )

            if (isSaved) {
                Toast.makeText(this, "Record saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to save record", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
