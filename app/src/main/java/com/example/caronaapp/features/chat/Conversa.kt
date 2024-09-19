package com.example.caronaapp.features.chat

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.EnviarMensagem
import com.example.caronaapp.ui.theme.SetaEsquerda

@Composable
fun ConversaScreen(navController: NavController) {
    CaronaAppTheme {
        val scrollState = rememberScrollState()

        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxHeight()
                                .size(24.dp)
                        ) {
                            Icon(
                                imageVector = SetaEsquerda,
                                contentDescription = "Voltar",
                                tint = Azul,
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.mipmap.user_default),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Lucas Arantes",
                                color = Azul,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier,
                        color = CinzaE8,
                        thickness = 1.dp
                    )
                }
            },
            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(88.dp),
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
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(CinzaF5),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var novaMensagem by remember { mutableStateOf("") }

                        TextField(
                            value = novaMensagem,
                            onValueChange = { novaMensagem = it },
                            placeholder = {
                                Text(
                                    text = "Digite sua mensagem",
                                    color = Cinza90,
                                    style = MaterialTheme.typography.displayLarge
                                )
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.85f)
                                .background(Color.Transparent),
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

                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxHeight()
                                .size(24.dp)
                        ) {
                            Icon(
                                imageVector = EnviarMensagem,
                                contentDescription = "Voltar",
                                tint = Azul,
                            )
                        }
                    }

                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                Mensagem(
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .padding(vertical = 8.dp),
                    mensagem = "Olá, bom dia! Tudo bem?",
                    horario = "16:00h",
                    enviada = true
                )
                Mensagem(
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .padding(vertical = 8.dp),
                    mensagem = "Por favor, pode confirmar o local de partida?",
                    horario = "16:00h",
                    enviada = true
                )
                Mensagem(
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(vertical = 8.dp),
                    mensagem = "Bom dia! Tudo bem e você?",
                    horario = "16:01h",
                    enviada = false
                )
                Mensagem(
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(vertical = 8.dp),
                    mensagem = "Av. Paulista, 2000",
                    horario = "16:01h",
                    enviada = false
                )
            }
        }
    }
}

@Preview
@Composable
fun ConversaScreenPreview() {
    val navController = rememberNavController()
    ConversaScreen(navController = navController)
}