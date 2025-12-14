package com.example.medquiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_stats")
data class DailyStatsEntity(
    @PrimaryKey
    val date: String,
    val totalSeen: Int = 0,
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val emptyCount: Int = 0
)