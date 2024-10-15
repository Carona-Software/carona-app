package com.example.caronaapp.data.entity

data class Endereco(
    val id: Int,
    val cep: String,
    val uf: String,
    val cidade: String,
    val bairro: String,
    val logradouro: String,
    val numero: Int,
    val latitude: Double,
    val longitude: Double
)
