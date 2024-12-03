package com.example.caronaapp.presentation.screens.feature.conversas

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.data.dto.chat.ChatItem
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.presentation.screens.feature.chat.ChatViewModel
import com.example.caronaapp.presentation.view_models.PerfilOutroUsuarioViewModel
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.utils.layout.BottomNavBar
import com.example.caronaapp.utils.layout.CustomAsyncImage
import com.example.caronaapp.utils.layout.CustomDefaultImage
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val sheetState = rememberModalBottomSheetState()


    val viewModel = koinViewModel<HomeViewModel>()
    val chatViewModel = koinViewModel<ChatViewModel>()

    val searchedUsers by viewModel.searchedUsers.collectAsState()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

    val chatItems by chatViewModel.chatList.collectAsState()
    val perfilUser by viewModel.perfilUser.collectAsState()

    var usuarioPesquisado by remember { mutableStateOf("") }
    val isLoading = searchedUsers.isEmpty() && usuarioPesquisado.isNotEmpty()

    LaunchedEffect(usuarioPesquisado) {
        if (usuarioPesquisado.isNotBlank()) {
            viewModel.searchUsers(usuarioPesquisado)
        } else {
            viewModel.clearSearch()
        }
    }

    LaunchedEffect(chatItems) {
        Log.d("HomeScreen", "Chat items: ${chatItems.size}")
    }

    CaronaAppTheme {
        Scaffold(
            containerColor = Color.White,
            bottomBar = { BottomNavBar(navController = navController, perfilUser = perfilUser) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                SearchField(
                    searchText = usuarioPesquisado,
                    onSearchTextChanged = { usuarioPesquisado = it }
                )

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Carregando...", style = MaterialTheme.typography.bodyMedium)
                    }
                } else if (usuarioPesquisado.isNotBlank()) {
                    UserList(
                        users = searchedUsers,
                        currentUserId = currentUserId,
                        navController = navController
                    )
                } else {
                    ChatListScreen(chatItems = chatItems, navController = navController)
                }
            }
        }
    }
}
@Composable
fun ChatListScreen(chatItems: List<ChatItem>, navController: NavController) {
    if (chatItems.isEmpty()) {
        NoResultsComponent(text = stringResource(id = R.string.nenhuma_conversa_encontrada))
    } else {
        LazyColumn {
            items(chatItems) { chatItem ->
                ChatListItem(chatItem = chatItem, navController = navController)
            }
        }
    }
}

@Composable
fun ChatListItem(chatItem: ChatItem, navController: NavController) {
    val perfilOutroUsuarioViewModel = koinViewModel<PerfilOutroUsuarioViewModel>()
    val state by perfilOutroUsuarioViewModel.searchedUser.collectAsState()

    val chatViewModel = koinViewModel<ChatViewModel>()
    Column {
        Row(modifier = Modifier.padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate(
                            "chat/${chatItem.chatId}/${
                                Uri.encode(
                                    chatItem?.userName ?: ""
                                )
                            }/${Uri.encode(chatItem?.fotoUrl ?: "")}"
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (chatItem.isFotoValida) {
                    CustomAsyncImage(
                        fotoUrl = chatItem.fotoUrl,
                        modifier = Modifier.size(50.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Azul)
                    ) {
                        Text(
                            text = chatItem.userName.firstOrNull()?.uppercase() ?: "",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = chatItem.userName,
                        style = MaterialTheme.typography.labelLarge,
                        color = Azul
                    )
                    Text(
                        text = chatItem.lastMessage,
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            if (chatItem.isUnread) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Azul)
                        .align(Alignment.CenterVertically)
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = CinzaE8,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )
    }
    LaunchedEffect(chatItem.chatId) {
        if (!chatItem.isUnread) {
            chatViewModel.markChatAsRead(
                chatItem.chatId,
                FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
            )
        }
    }
}

@Composable
fun SearchField(searchText: String, onSearchTextChanged: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CinzaF5),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            placeholder = { Text(text = "Procurar usuário...", color = Cinza90, style = MaterialTheme.typography.displayLarge) },
            modifier = Modifier.fillMaxHeight(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Azul,
                unfocusedTextColor = Azul,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.headlineMedium
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Procurar",
            tint = Cinza90,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(28.dp)
        )
    }
}

@Composable
fun UserList(users: List<UsuarioCriacaoDto>, currentUserId: String, navController: NavController) {
    if (users.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Nenhum usuário encontrado",
                style = MaterialTheme.typography.bodyMedium,
                color = Cinza90
            )
        }
    } else {
        LazyColumn {
            items(users) { user ->
                UserItem(
                    user = user,
                    currentUserId = currentUserId,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun UserItem(user: UsuarioCriacaoDto, currentUserId: String, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val chatViewModel = koinViewModel<ChatViewModel>()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable {
                coroutineScope.launch {
                    chatViewModel.createOrGetChat(
                        currentUserId = currentUserId,
                        targetUserId = user.userId
                    ) { chatId ->
                        navController.navigate(
                            "chat/${chatId}/${
                                Uri.encode(
                                    user?.nome ?: ""
                                )
                            }/${Uri.encode(user?.fotoUrl ?: "")}"
                        )
                    }
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (user.isFotoValida) {
            CustomAsyncImage(
                fotoUrl = user.fotoUrl,
                modifier = Modifier.size(50.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Azul)
            ) {
                Text(
                    text = user.nome.firstOrNull()?.uppercase() ?: "",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(text = user.nome, color = Azul, style = MaterialTheme.typography.labelLarge)
            Text(text = user.email, color = Cinza90, style = MaterialTheme.typography.displayLarge)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}