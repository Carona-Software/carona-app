package com.example.caronaapp.presentation.screens.onboarding

import androidx.annotation.DrawableRes
import com.example.caronaapp.R

data class Page(
    @DrawableRes val image: Int,
    val text: String,
)

val pages = listOf(
    Page(
        image = R.drawable.onboarding_objetivo,
        text = "Você oferece ou reserva carona de forma simples e segura"
    ),
    Page(
        image = R.drawable.onboarding_feedback,
        text = "Você avalia o motorista ou passageiro e contribui com a comunidade"
    ),
    Page(
        image = R.drawable.onboarding_apenas_mulheres,
        text = "Viagens seguras para as mulheres, com a opção apenas mulheres"
    ),
    Page(
        image = R.drawable.onboarding_fidelizados,
        text = "Tenha preferência na reserva de carona, se fidelizando ao motorista"
    ),
)