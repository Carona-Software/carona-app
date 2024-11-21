package com.example.caronaapp.utils.layout

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun CustomAsyncImage(
    modifier: Modifier = Modifier,
    fotoUrl: String
) {
    AsyncImage(
        model = fotoUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .then(modifier)
    )
}