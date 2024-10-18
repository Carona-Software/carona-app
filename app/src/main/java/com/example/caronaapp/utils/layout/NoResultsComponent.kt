package com.example.caronaapp.utils.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul

@Composable
fun NoResultsComponent(
    text: String,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_result_image),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = text,
            color = Azul,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.width(240.dp),
            textAlign = TextAlign.Center
        )
    }
}