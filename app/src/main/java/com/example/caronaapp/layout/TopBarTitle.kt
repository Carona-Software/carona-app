package com.example.caronaapp.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.SetaEsquerda

@Composable
fun TopBarTitle(navController: NavController, title: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 16.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = SetaEsquerda,
                contentDescription = "Voltar",
                tint = Azul,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = title ?: "",
            color = Azul,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxSize(0.8f),
            textAlign = TextAlign.Center
        )
    }
}