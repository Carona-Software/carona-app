package com.example.caronaapp.utils.layout

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.SetaEsquerda

@Composable
fun TopBarUser(
    navController: NavController,
    fotoUser: Painter?,
    nome: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White),
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
                    painter = fotoUser ?: painterResource(id = R.mipmap.user_default),
                    contentDescription = nome
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = nome,
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
}