package com.example.healthmate

data class HealthRecord(
    val id: Int,
    val steps: Int,
    val calories: Int,
    val water: Int,
    val date: String
)
