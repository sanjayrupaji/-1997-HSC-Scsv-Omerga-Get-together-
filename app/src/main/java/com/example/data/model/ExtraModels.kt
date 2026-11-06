package com.example.data.model

data class CommitteeMember(
    val name: String,
    val role: String,
    val phone: String,
    val location: String
)

data class CollegeMemoryItem(
    val title: String,
    val tag: String,
    val description: String,
    val yearTag: String = "१९९५-१९९७"
)
