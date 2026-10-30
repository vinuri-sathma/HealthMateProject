package com.example.healthmate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddRecord = findViewById<Button>(R.id.btnAddRecord)
        val btnViewRecords = findViewById<Button>(R.id.btnViewRecords)

        btnAddRecord.setOnClickListener {
            val intent = Intent(this, AddRecordActivity::class.java)
            startActivity(intent)
        }

        btnViewRecords.setOnClickListener {
            val intent = Intent(this, ViewRecordsActivity::class.java)
            startActivity(intent)
        }
    }
}
