package com.example.caronaapp.data.dto.carro

data class CarroCriacaoDto(
    var id: Int? = null,
    var cor: String = "",
    var marca: String = "",
    var modelo: String = "",
    var placa: String = "",
    var fkUsuario: Int = 0
)
