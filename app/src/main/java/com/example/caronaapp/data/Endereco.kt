package com.example.caronaapp.data

import com.google.gson.annotations.SerializedName

data class Endereco(
    val id: Int? = null,
    var cep: String = "",
    var uf: String,
    @SerializedName("localidade") var cidade: String,
    var bairro: String,
    var logradouro: String,
    var numero: Int = 0
)