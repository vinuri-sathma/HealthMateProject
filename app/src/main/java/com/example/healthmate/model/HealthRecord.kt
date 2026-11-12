package com.example.healthmate.model

data class HealthRecord(
    val id: Int,
    val steps: Int,
    val calories: Int,
    val water: Int,
    val date: String
)
