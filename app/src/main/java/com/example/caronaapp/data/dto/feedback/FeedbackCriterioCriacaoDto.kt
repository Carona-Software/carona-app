package com.example.caronaapp.data.dto.feedback

import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioCriacaoDto
import java.time.LocalDate

data class FeedbackCriterioCriacaoDto(
    val remetenteId: Int = 0,
    val destinatarioId: Int = 0,
    val comentario: String = "",
    val data: LocalDate = LocalDate.now(),
    val viagemId: Int = 0,
    val notasCriterios: List<NotaCriterioCriacaoDto>? = null
)
