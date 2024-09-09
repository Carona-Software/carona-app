package com.example.caronaapp.cadastro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.caronaapp.layout.ButtonAction
import com.example.caronaapp.layout.CustomCard

@Composable
fun CadastroPessoais() {
    CustomCard(
        shadowElevation = 16f, // Intensidade da sombra
        shadowOffsetY = 18f, // Desloca a sombra mais para cima
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Nome") }
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Email") }
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "CPF") }
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Data de Nascimento") }
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Data de Nascimento") }
            )
            ButtonAction(handleClick = {})
        }
    }
}