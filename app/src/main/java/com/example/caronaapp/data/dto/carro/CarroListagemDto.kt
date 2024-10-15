package com.example.caronaapp.data.dto.carro

data class CarroListagemDto(
    val id: Int,
    val cor: String,
    val marca: String,
    val modelo: String,
    val placa: String,
    val motorista: MotoristaListagemDto
) {
    data class MotoristaListagemDto(
        val id: Int,
        val nome: String
    )
}
