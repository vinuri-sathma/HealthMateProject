package com.example.healthmate

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthmate.adapter.HealthRecordAdapter

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

        loadRecords()
    }

    private fun loadRecords() {
        val records = databaseHelper.getAllRecords()

        if (records.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            recyclerView.adapter = HealthRecordAdapter(records) { record ->
                showDeleteConfirmation(record)
            }
        }
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
}
