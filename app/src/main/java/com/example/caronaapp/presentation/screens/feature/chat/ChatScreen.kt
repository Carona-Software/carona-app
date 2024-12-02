package com.example.caronaapp.presentation.screens.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.caronaapp.data.dto.chat.Message
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulMensagem
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.utils.functions.convertTimestampToLocalTime
import com.example.caronaapp.utils.functions.formatTime
import com.example.caronaapp.utils.layout.TopBarUser
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatScreen(navController: NavController, fotoUrl: String, chatId: String, userBName: String) {
    Scaffold {
        val viewModel: ChatViewModel = koinViewModel()
        val messages = viewModel.messages.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White)
        ) {
            LaunchedEffect(key1 = true) {
                viewModel.listenForMessages(chatId)
            }
            TopBarUser(
                navController = navController,
                fotoUrl = fotoUrl,
                isUrlFotoValida = false,
                nome = userBName
            )
            HorizontalDivider(
                modifier = Modifier,
                color = CinzaE8,
                thickness = 1.dp
            )

            ChatMessages(
                navController,
                messages = messages.value,
                onSendMessage = { messageText ->
                    viewModel.sendMessage(chatId, messageText)
                }
            )
        }
    }
}


@Composable
fun ChatMessages(
    navController: NavController,
    messages: List<Message>,
    onSendMessage: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var msg = remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center
            ) {
                HorizontalDivider(
                    modifier = Modifier,
                    color = CinzaE8,
                    thickness = 1.dp
                )
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CinzaF5),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = msg.value,
                        onValueChange = { msg.value = it },
                        modifier = Modifier.fillMaxWidth(0.85f),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Azul,
                            unfocusedTextColor = Azul,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = {
                            Text(
                                text = "Digite uma mensagem",
                                color = Cinza90,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                onSendMessage(msg.value)
                                msg.value = ""
                                keyboardController?.hide()
                            }
                        )
                    )

                    IconButton(onClick = {
                        onSendMessage(msg.value)
                        msg.value = ""
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "send",
                            tint = Azul
                        )
                    }
                }

            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                items(messages) { message ->
                    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid
                    ChatBubble(
                        message = message,
                        modifier = Modifier.align(if (isCurrentUser) Alignment.End else Alignment.Start)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message, modifier: Modifier) {
    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid
    val bubbleColor = if (isCurrentUser) AzulMensagem else CinzaF5
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 260.dp)
                .padding(horizontal = 8.dp)
                .background(
                    color = bubbleColor, RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomEnd = if (isCurrentUser) 0.dp else 20.dp,
                        bottomStart = if (isCurrentUser) 20.dp else 0.dp
                    )
                )
                .align(if (isCurrentUser) Alignment.End else Alignment.Start)

        ) {
            Text(
                text = message.message?.trim() ?: "",
                color = Azul,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(8.dp)
            )

        }
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(if (isCurrentUser) Alignment.End else Alignment.Start)
        ) {
            Text(
                text = formatTime(convertTimestampToLocalTime(message.timestamp)),
                color = Cinza90,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

}