package com.example.caronaapp.presentation.screens.viagens

sealed class ViagensField {
    data class CapacidadePassageiros(val value: Int) : ViagensField()
    data class PrecoMinimo(val value: Double) : ViagensField()
    data class PrecoMaximo(val value: Double) : ViagensField()
    data class ApenasMulheres(val value: Boolean) : ViagensField()
}

data class ViagensState(
    val showBottomSheet: Boolean = false,
    val capacidadePassageiros: Int = 1,
    val precoMinimo: Double = 0.0,
    val precoMaximo: Double = 0.0,
    val apenasMulheres: Boolean = false,
    val data: String = "",
    val pontoPartida: String = "",
    val pontoDestino: String = "",
)