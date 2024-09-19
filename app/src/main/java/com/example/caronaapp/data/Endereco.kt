package com.example.caronaapp.data

data class Endereco(
    val id: Int? = null,
    var cep: String = "",
    var uf: String = "",
    var cidade: String = "",
    var bairro: String = "",
    var logradouro: String = "",
    var numero: Int = 0
)