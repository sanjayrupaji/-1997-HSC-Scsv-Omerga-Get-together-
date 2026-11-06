package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reunion_events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timeSlot: String,
    val titleMarathi: String,
    val titleEnglish: String,
    val location: String,
    val descriptionMarathi: String,
    val iconName: String,
    val isHighlight: Boolean = false,
    val orderIndex: Int = 0
)
