package com.example.caronaapp.presentation.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.presentation.screens.onboarding.components.OnboardingButton
import com.example.caronaapp.presentation.screens.onboarding.components.OnboardingPage
import com.example.caronaapp.presentation.screens.onboarding.components.PageIndicator
import com.example.caronaapp.presentation.view_models.OnboardingViewModel
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val isOnboardingDone by viewModel.isOnboardingDone.collectAsState()

    LaunchedEffect(key1 = isOnboardingDone) {
        if (isOnboardingDone) {
            navController.popBackStack()
            navController.navigate("login") {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0) { pages.size }
    val buttonsState = remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> listOf("", "Próximo")
                1 -> listOf("Voltar", "Próximo")
                2 -> listOf("Voltar", "Próximo")
                3 -> listOf("Voltar", "Iniciar")
                else -> listOf("", "")
            }
        }
    }

    CaronaAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            HorizontalPager(state = pagerState) { index ->
                OnboardingPage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp)
                        .weight(1f),
                    page = pages[index]
                )
            }

            Spacer(modifier = Modifier.height(92.dp))

            Row( // page indicator
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                PageIndicator(
                    modifier = Modifier.width(100.dp),
                    pagesSize = pages.size,
                    selectedPage = pagerState.currentPage
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            Row( // buttons
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (buttonsState.value[0].isNotBlank()) {
                    OnboardingButton(
                        containerColor = Color.White,
                        textColor = Azul,
                        text = buttonsState.value[0]
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                OnboardingButton(
                    containerColor = Azul,
                    textColor = Color.White,
                    text = buttonsState.value[1]
                ) {
                    scope.launch {
                        if (pagerState.currentPage == 3) {
                            viewModel.setOnboardingDone()
                        } else {
                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(rememberNavController())
}