package com.example.caronaapp.presentation.screens.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caronaapp.presentation.screens.onboarding.Page
import com.example.caronaapp.presentation.screens.onboarding.pages
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme

@Composable
fun OnboardingPage(
    modifier: Modifier = Modifier,
    page: Page
) {
    CaronaAppTheme {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = page.image),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = page.text,
                color = Azul,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun OnboardingPagePreview() {
    OnboardingPage(page = pages[0])
}