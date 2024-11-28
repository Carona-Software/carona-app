package com.example.caronaapp.presentation.screens.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CinzaD9

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pagesSize: Int,
    selectedPage: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(pagesSize) { page ->
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(if (page == selectedPage) Azul else CinzaD9)
            )
        }
    }
}