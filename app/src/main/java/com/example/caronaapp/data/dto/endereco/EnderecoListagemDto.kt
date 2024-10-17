package com.example.caronaapp.data.dto.endereco

data class EnderecoListagemDto(
    val id: Int,
    val cep: String,
    val uf: String,
    val cidade: String,
    val bairro: String,
    val logradouro: String,
    val numero: Int,
    val latitude: Double? = null,
    val longitude: Double? = null
)
