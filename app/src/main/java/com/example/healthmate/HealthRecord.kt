package com.example.healthmate

data class HealthRecord(
    val id: Int,
    val steps: Int,
    val calories: Double,
    val water: Int,
    val date: String
)
