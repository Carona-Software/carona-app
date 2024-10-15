package com.example.caronaapp.data.entity

import com.example.caronaapp.data.enums.StatusViagem
import java.time.LocalDate
import java.time.LocalTime

data class Viagem(
    val id: Int,
    val capacidadePassageiros: Int,
    val apenasMulheres: Boolean,
    val data: LocalDate,
    val hora: LocalTime,
    val preco: Double,
    val statusViagem: StatusViagem,
    val carro: Carro,
    val enderecoPartida: Endereco,
    val enderecoChegada: Endereco,
)
