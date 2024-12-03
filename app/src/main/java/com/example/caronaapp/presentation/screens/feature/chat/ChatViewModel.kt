package com.example.caronaapp.presentation.screens.feature.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.caronaapp.data.dto.chat.ChatData
import com.example.caronaapp.data.dto.chat.ChatItem
import com.example.caronaapp.data.dto.chat.Message
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _userChats = MutableStateFlow<List<ChatData>>(emptyList())
    val userChats = _userChats.asStateFlow()

    private val _chatList = MutableStateFlow<List<ChatItem>>(emptyList())
    val chatList: StateFlow<List<ChatItem>> = _chatList

    init {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
        observeUserChats(currentUserId)
    }


    fun listenForMessages(chatId: String) {
        val currentUserId = auth.currentUser?.uid.orEmpty()

        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e("ListenForMessages", "Erro: ${exception.message}")
                    return@addSnapshotListener
                }

                val messagesList = snapshot?.documents?.map { document ->
                    document.toObject(Message::class.java) ?: Message()
                } ?: emptyList()

                Log.d("ListenForMessages", "Mensagens carregadas: ${messagesList.size}")
                _messages.value = messagesList

                markChatAsRead(chatId, currentUserId)
            }
    }

    fun sendMessage(chatId: String, messageText: String) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val message = Message(
            senderId = currentUserId,
            message = messageText,
            timestamp = System.currentTimeMillis()
        )

        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(message)
            .addOnSuccessListener {
                updateLastMessage(chatId, messageText)
                markUnreadForRecipient(chatId, currentUserId)
            }
            .addOnFailureListener { exception ->
                Log.e("ChatViewModel", "Erro ao enviar mensagem: ${exception.message}")
            }
    }

    private fun updateLastMessage(chatId: String, lastMessage: String) {
        db.collection("chats")
            .document(chatId)
            .update("lastMessage", lastMessage, "timestamp", System.currentTimeMillis())
            .addOnSuccessListener {
                Log.d("ChatViewModel", "Last message updated successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("ChatViewModel", "Erro ao atualizar última mensagem: ${exception.message}")
            }
    }


    fun observeMessages(chatId: String) {
        db.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("ObserveMessages", "Erro ao observar mensagens: ${error.message}")
                    return@addSnapshotListener
                }

                val messages = snapshot?.documents?.mapNotNull { document ->
                    document.toObject(Message::class.java)
                }
                _messages.value = messages ?: emptyList()
            }
    }

    suspend fun createOrGetChat(
        currentUserId: String,
        targetUserId: String,
        onChatReady: (String) -> Unit
    ) {
        if (currentUserId.isBlank() || targetUserId.isBlank()) {
            Log.e(
                "ChatViewModel",
                "IDs de usuário inválidos: currentUserId=$currentUserId, targetUserId=$targetUserId"
            )
            return
        }

        val sortedIds = listOf(currentUserId, targetUserId).sorted()
        val chatId = sortedIds.joinToString("_")

        val chatRef = db.collection("chats").document(chatId)

        try {
            val snapshot = chatRef.get().await()
            if (snapshot.exists()) {
                onChatReady(chatId)
            } else {
                // Criar novo chat
                val chatData = mapOf(
                    "participants" to sortedIds,
                    "timestamp" to System.currentTimeMillis()
                )
                chatRef.set(chatData).await()
                chatRef.get().await()
                onChatReady(chatId)
            }
        } catch (e: Exception) {
            Log.e("ChatViewModel", "Erro ao buscar/criar chat: ${e.message}")
        }
    }

    private suspend fun createChat(
        currentUserId: String,
        targetUserId: String,
        onChatReady: (String) -> Unit
    ) {
        val chatRef = db.collection("chats").document()

        val chatData = mapOf(
            "participants" to listOf(currentUserId, targetUserId),
            "timestamp" to System.currentTimeMillis()
        )

        try {
            chatRef.set(chatData).await()
            onChatReady(chatRef.id)
        } catch (e: Exception) {
            Log.e("ChatViewModel", "Erro ao criar chat: ${e.message}")
        }
    }



    fun fetchUserDetails(userId: String, onResult: (UsuarioCriacaoDto) -> Unit) {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                val userData = document.toObject(UsuarioCriacaoDto::class.java)
                if (userData != null) {
                    onResult(userData)
                    Log.i("ChatViewModel", "userData infos: ${userData}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "fetchUserDetails",
                    "Erro ao buscar detalhes do usuário: ${exception.message}"
                )
            }
    }


    fun observeUserChats(currentUserId: String) {
        FirebaseFirestore.getInstance()
            .collection("chats")
            .whereArrayContains("participants", currentUserId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("ChatViewModel", "Erro ao ouvir atualizações de chats: ${error.message}")
                    return@addSnapshotListener
                }

                val chatList = mutableListOf<ChatItem>()

                snapshot?.documents?.forEach { document ->
                    val participants = document.get("participants") as? List<String> ?: return@forEach
                    val lastMessage = document.getString("lastMessage") ?: ""
                    val isRead = document.getBoolean("${currentUserId}_read") ?: true
                    val chatId = document.id
                    val fotoUrl = document.getString("fotoUrl") ?: ""
                    val otherUserId = participants.firstOrNull { it != currentUserId } ?: ""
                    val lastMessageTime = document.getLong("timestamp") ?: 0L

                    fetchUserDetails(otherUserId) { userData ->
                        chatList.add(
                            ChatItem(
                                chatId = chatId,
                                userName = userData.nome,
                                fotoUrl = userData.fotoUrl,
                                lastMessage = lastMessage,
                                lastMessageTime = lastMessageTime,
                                isFotoValida = true,
                                isUnread = !isRead
                            )
                        )

                        // Atualizar o StateFlow com os chats
                        _chatList.value = chatList.sortedByDescending { it.lastMessageTime }
                    }
                }
            }
    }

    private fun markUnreadForRecipient(chatId: String, senderId: String) {
        db.collection("chats").document(chatId).get()
            .addOnSuccessListener { document ->
                val participants = document.get("participants") as? List<String> ?: return@addOnSuccessListener
                val recipientId = participants.firstOrNull { it != senderId } ?: return@addOnSuccessListener

                db.collection("chats")
                    .document(chatId)
                    .update("${recipientId}_read", false)
            }
            .addOnFailureListener { exception ->
                Log.e("ChatViewModel", "Erro ao marcar mensagem como não lida: ${exception.message}")
            }
    }

    fun markChatAsRead(chatId: String, currentUserId: String) {
        FirebaseFirestore.getInstance()
            .collection("chats")
            .document(chatId)
            .update("${currentUserId}_read", true)
            .addOnSuccessListener {
                Log.d("ChatViewModel", "Chat marcado como lido.")
            }
            .addOnFailureListener { error ->
                Log.e("ChatViewModel", "Erro ao marcar chat como lido: ${error.message}")
            }
    }
}




