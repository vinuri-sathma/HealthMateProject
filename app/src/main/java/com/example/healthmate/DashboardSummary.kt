package com.example.healthmate

data class DashboardSummary(
    val totalSteps: Int,
    val totalCalories: Int,
    val totalWater: Int,
    val waterGoal: Int,
    val waterPercentage: Int
)