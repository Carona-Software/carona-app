package com.example.caronaapp.features.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.layout.BottomNavBar
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.CinzaF5

class Chat : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaronaAppTheme {
                ChatScreen()
            }
        }
    }
}

@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    var usuarioPesquisado by remember {
        mutableStateOf("")
    }

    var conversasRecentes = remember {
        mutableStateListOf<ConversaData>(
            ConversaData(
                fotoUser = null,
                nomeUser = "Kauã Queiroz",
                ultimaMensagem = "Muito obrigado"
            ),
            ConversaData(
                fotoUser = null,
                nomeUser = "Gustavo Medeiros"
            ),
        )
    }

    CaronaAppTheme {
        Scaffold(
            bottomBar = { BottomNavBar() }
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CinzaF5),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = usuarioPesquisado,
                        onValueChange = { usuarioPesquisado = it },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.procurar_usuario),
                                color = Cinza90,
                                style = MaterialTheme.typography.displayLarge
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .fillMaxHeight()
                            .background(Color.Transparent),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Azul,
                            unfocusedTextColor = Azul,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.procurar),
                        tint = Cinza90,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(items = conversasRecentes) { conversa ->
                        Conversa(
                            fotoUser = painterResource(id = R.mipmap.foto_gustavo),
                            nomeUser = conversa.nomeUser,
                            ultimaMensagem = conversa.ultimaMensagem
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = CinzaE8,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

class ConversaData(
    val fotoUser: Painter? = null,
    val nomeUser: String,
    val ultimaMensagem: String? = null
)

@Composable
fun Conversa(
    fotoUser: Painter,
    nomeUser: String,
    ultimaMensagem: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
//            .background(BrancoF1)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.mipmap.foto_gustavo),
            contentDescription = "Usuário",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            verticalArrangement = if (ultimaMensagem != null) Arrangement.SpaceEvenly else Arrangement.Center
        ) {
            Text(
                text = "Gustavo Medeiros Silva",
                color = Azul,
                style = MaterialTheme.typography.labelLarge
            )
            if (ultimaMensagem != null) {
                Text(
                    text = ultimaMensagem,
                    color = Cinza90,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    CaronaAppTheme {
        ChatScreen()
    }
}