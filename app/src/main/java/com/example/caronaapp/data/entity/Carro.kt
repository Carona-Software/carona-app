package com.example.caronaapp.data.entity

data class Carro(
    val id: Int,
    val cor: String,
    val marca: String,
    val modelo: String,
    val placa: String,
    val usuario: Usuario? = null
)
