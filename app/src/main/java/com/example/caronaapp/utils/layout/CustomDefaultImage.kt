package com.example.caronaapp.utils.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.caronaapp.R

@Composable
fun CustomDefaultImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.mipmap.user_default),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .then(modifier)
    )
}