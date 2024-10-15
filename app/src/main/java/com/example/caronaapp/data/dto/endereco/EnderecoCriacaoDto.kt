package com.example.caronaapp.data.dto.endereco

import com.google.gson.annotations.SerializedName

data class EnderecoCriacaoDto(
    val id: Int? = null,
    var cep: String = "",
    var uf: String,
    @SerializedName("localidade") var cidade: String,
    var bairro: String,
    var logradouro: String,
    var numero: Int = 0
)