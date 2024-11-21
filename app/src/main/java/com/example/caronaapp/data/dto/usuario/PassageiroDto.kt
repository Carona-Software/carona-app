package com.example.caronaapp.data.dto.usuario

data class PassageiroDto(
    val id: Int,
    val nome: String,
    val perfil: String,
    val fotoUrl: String,
    val notaGeral: Double,
    val isFotoValida: Boolean,
    val foiAvaliado: Boolean,
)
