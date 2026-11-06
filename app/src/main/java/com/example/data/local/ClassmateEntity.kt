package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classmates")
data class ClassmateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val nickname: String = "",
    val stream: String = "विज्ञान (Science)", // विज्ञान / कला / वाणिज्य
    val occupationCategory: String = "इतर", // व्यवसाय, शासकीय सेवा, वैद्यकीय, शिक्षण, शेती, कॉर्पोरेट
    val jobTitle: String = "",
    val organization: String = "",
    val nativeVillage: String = "उमरगा",
    val currentCity: String = "उमरगा",
    val address: String = "",
    val phone: String = "",
    val whatsapp: String = "",
    val email: String = "",
    val bloodGroup: String = "माहित नाही",
    val thoughtsAndMemories: String = "",
    val favoriteMemory: String = "",
    val attendanceStatus: String = "उपस्थित राहणार", // उपस्थित राहणार, नक्की नाही, गैरहजर
    val photoUrl: String = "",
    val avatarColorIndex: Int = 0,
    val isVerified: Boolean = true,
    val addedDate: Long = System.currentTimeMillis()
)
