package com.example.caronaapp.data.entity

import java.time.LocalDate

data class Feedback(
    val id: Int,
    val comentario: String,
    val data: LocalDate,
    val remetente: Usuario,
    val destinatario: Usuario,
    val viagem: Viagem
)
