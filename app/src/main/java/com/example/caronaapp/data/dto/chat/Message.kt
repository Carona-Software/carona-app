package com.example.caronaapp.data.dto.chat


data class Message(
    val senderId: String = "",
    val message: String = "",
    val timestamp: Long = 0L
)
