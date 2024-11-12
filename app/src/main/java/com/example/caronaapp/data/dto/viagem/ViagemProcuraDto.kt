package com.example.caronaapp.data.dto.viagem

data class ViagemProcuraDto(
    val pontoPartida: Coordenadas? = null,
    val pontoChegada: Coordenadas? = null,
    val data: String = "",
    val precoMinimo: Double? = null,
    val precoMaximo: Double? = null,
    val capacidadePassageiros: Int? = null,
    val apenasMulheres: Boolean? = null
)
