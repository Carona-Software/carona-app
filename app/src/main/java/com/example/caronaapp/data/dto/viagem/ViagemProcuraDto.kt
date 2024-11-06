package com.example.caronaapp.data.dto.viagem

import java.time.LocalDate

data class ViagemProcuraDto(
    val coordenadas: Coordenadas? = null,
    val data: LocalDate = LocalDate.now(),
    val precoMinimo: Double? = null,
    val precoMaximo: Double? = null,
    val capacidadePassageiros: Int? = null
)
