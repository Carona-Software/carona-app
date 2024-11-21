package com.example.caronaapp.utils.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.caronaapp.ui.theme.Azul

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    backGround: Color = Color.White
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backGround),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Azul)
    }
}