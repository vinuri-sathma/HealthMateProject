package com.example.healthmate

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthmate.adapter.HealthRecordAdapter

class ViewRecordsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_records)

        val recyclerView = findViewById<RecyclerView>(R.id.rvHealthRecords)
        val tvEmpty = findViewById<TextView>(R.id.tvEmptyRecords)

        val databaseHelper = DatabaseHelper(this)
        val records = databaseHelper.getAllRecords()

        if (records.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = HealthRecordAdapter(records)
        }
    }
}
