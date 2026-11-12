package com.example.healthmate.model

data class DashboardSummary(
    val totalSteps: Int,
    val totalCalories: Int,
    val totalWater: Int,
    val waterGoal: Int,
    val waterPercentage: Int
)