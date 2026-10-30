package com.example.healthmate

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthmate.adapter.HealthRecordAdapter
import android.app.DatePickerDialog
import java.util.Calendar
import java.util.Locale
import android.widget.Button



class ViewRecordsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_records)

        recyclerView = findViewById(R.id.rvHealthRecords)
        tvEmpty = findViewById(R.id.tvEmptyRecords)
        databaseHelper = DatabaseHelper(this)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val etSearchDate = findViewById<EditText>(R.id.etSearchDate)

        etSearchDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, year, month, day ->
                    val selectedDate = String.format(
                        Locale.US,
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        day
                    )

                    etSearchDate.setText(selectedDate)
                    loadRecords()

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val btnClearSearch = findViewById<Button>(R.id.btnClearSearch)

        btnClearSearch.setOnClickListener {
            etSearchDate.setText("")
            loadRecords()
        }



        loadRecords()
    }

    private fun loadRecords() {

        val selectedDate = findViewById<EditText>(R.id.etSearchDate)
            .text.toString()

        val allRecords = databaseHelper.getAllRecords()

        val records = if (selectedDate.isEmpty()) {
            allRecords
        } else {
            allRecords.filter { it.date == selectedDate }
        }


        if (records.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        recyclerView.adapter = HealthRecordAdapter(
            records,
            onDeleteClick = { record ->
                showDeleteConfirmation(record)
            },
            onEditClick = { record ->
                showEditDialog(record)
            }
        )
    }

    private fun showDeleteConfirmation(record: HealthRecord) {
        AlertDialog.Builder(this)
            .setTitle("Delete Health Record")
            .setMessage("Are you sure you want to delete this record?")
            .setPositiveButton("Delete") { _, _ ->
                val deleted = databaseHelper.deleteRecord(record.id)

                if (deleted) {
                    Toast.makeText(
                        this,
                        "Record deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    loadRecords()
                } else {
                    Toast.makeText(
                        this,
                        "Failed to delete record",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditDialog(record: HealthRecord) {
        val dialogView = layoutInflater.inflate(
            R.layout.dialog_edit_record,
            null
        )

        val etSteps = dialogView.findViewById<EditText>(R.id.etEditSteps)
        val etCalories = dialogView.findViewById<EditText>(R.id.etEditCalories)
        val etWater = dialogView.findViewById<EditText>(R.id.etEditWater)

        etSteps.setText(record.steps.toString())
        etCalories.setText(record.calories.toString())
        etWater.setText(record.water.toString())

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Health Record")
            .setView(dialogView)
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val steps = etSteps.text.toString().toIntOrNull()
                val calories = etCalories.text.toString().toDoubleOrNull()
                val water = etWater.text.toString().toIntOrNull()

                if (
                    steps == null || steps < 0 ||
                    calories == null || !calories.isFinite() || calories < 0 ||
                    water == null || water < 0
                ) {
                    Toast.makeText(
                        this,
                        "Please enter valid values",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val updated = databaseHelper.updateRecord(
                        record.id,
                        steps,
                        calories,
                        water
                    )

                    if (updated) {
                        Toast.makeText(
                            this,
                            "Record updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        dialog.dismiss()
                        loadRecords()
                    } else {
                        Toast.makeText(
                            this,
                            "Failed to update record",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        dialog.show()
    }
}
