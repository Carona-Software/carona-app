package com.example.caronaapp.data.dto.viagem

data class LocalidadeDto(
    val coordenadas: Coordenadas,
    val cep: String,
    val uf: String,
    val cidade: String,
    val bairro: String,
    val logradouro: String,
    val numero: Int
)
