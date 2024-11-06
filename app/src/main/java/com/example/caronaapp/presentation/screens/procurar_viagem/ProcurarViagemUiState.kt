package com.example.caronaapp.presentation.screens.procurar_viagem

import java.time.LocalDate

sealed class ProcurarViagemField {
    data class PontoPartida(val value: String) : ProcurarViagemField()
    data class PontoChegada(val value: String) : ProcurarViagemField()
    data class Data(val value: LocalDate) : ProcurarViagemField()
}

data class ProcurarViagemState(
    val pontoPartida: String = "",
    val pontoChegada: String = "",
    val data: LocalDate = LocalDate.now(),
    val enderecoPontoPartida: String = "",
)