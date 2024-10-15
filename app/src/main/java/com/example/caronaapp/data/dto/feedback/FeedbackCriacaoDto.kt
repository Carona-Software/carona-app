package com.example.caronaapp.data.dto.feedback

import java.time.LocalDate

data class FeedbackCriacaoDto(
    val remetenteId: Int,
    val destinatarioId: Int,
    val comentario: String,
    val data: LocalDate,
    val viagemId: Int
)
