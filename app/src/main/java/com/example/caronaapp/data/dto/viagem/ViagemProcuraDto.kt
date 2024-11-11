package com.example.caronaapp.data.dto.viagem

import java.time.LocalDate

data class ViagemProcuraDto(
    val pontoPartida: Coordenadas? = null,
    val pontoChegada: Coordenadas? = null,
    val data: LocalDate = LocalDate.now(),
    val precoMinimo: Double? = null,
    val precoMaximo: Double? = null,
    val capacidadePassageiros: Int? = null,
    val apenasMulheres: Boolean? = null
)
