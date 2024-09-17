package com.example.caronaapp.data

data class Endereco(
    var cep: String = "",
    var uf: String = "",
    var cidade: String = "",
    var bairro: String = "",
    var logradouro: String = "",
    var numero: Int = 0
)