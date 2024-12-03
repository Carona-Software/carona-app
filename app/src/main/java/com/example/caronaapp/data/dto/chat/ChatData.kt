package com.example.caronaapp.data.dto.chat

data class ChatData(
    val chatId: String,
    val otherUserId: String,
    val lastMessage: String,
    val timestamp: Long
)

data class ChatItem(
    val chatId: String,
    val userName: String,
    val fotoUrl: String,
    val isFotoValida: Boolean = true,
    val lastMessage: String,
    val lastMessageTime: Long,
    val isUnread: Boolean
)
