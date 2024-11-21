package com.example.caronaapp.data.dto.usuario

data class FidelizadoListagemDto(
    val id: Int,
    val nome: String,
    val fotoUrl: String,
    val ufLocalidade: String,
    val cidadeLocalidade: String,
    val notaGeral: Double,
    val qtdViagensJuntos: Int,
    var isFotoValida: Boolean,
)
