package com.example.healthmate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthmate.HealthRecord
import com.example.healthmate.R
import android.widget.Button


class HealthRecordAdapter(
    private val records: List<HealthRecord>,
    private val onDeleteClick: (HealthRecord) -> Unit,
    private val onEditClick: (HealthRecord) -> Unit

) : RecyclerView.Adapter<HealthRecordAdapter.RecordViewHolder>() {


    class RecordViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvDate: TextView = itemView.findViewById(R.id.tvRecordDate)
        val tvSteps: TextView = itemView.findViewById(R.id.tvRecordSteps)
        val tvCalories: TextView = itemView.findViewById(R.id.tvRecordCalories)
        val tvWater: TextView = itemView.findViewById(R.id.tvRecordWater)

        val btnDelete: Button = itemView.findViewById(R.id.btnDeleteRecord)

        val btnEdit: Button = itemView.findViewById(R.id.btnEditRecord)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_health_record, parent, false)

        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = records[position]

        holder.tvDate.text = record.date
        holder.tvSteps.text = "Steps: ${record.steps}"
        holder.tvCalories.text = "Calories: ${record.calories} kcal"
        holder.tvWater.text = "Water Intake: ${record.water} ml"
        holder.btnDelete.setOnClickListener {
            onDeleteClick(record)
        }
        holder.btnEdit.setOnClickListener {
            onEditClick(record)
        }


    }

    override fun getItemCount(): Int {
        return records.size
    }
}
