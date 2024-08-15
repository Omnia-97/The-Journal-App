package com.example.journalkotlin

import com.google.firebase.Timestamp

data class Journal(
    val title: String,
    val thoughts: String,
    val imageUrl: Int,
    val timestamp: Timestamp,
    val userId: String,
    val username: String
)
