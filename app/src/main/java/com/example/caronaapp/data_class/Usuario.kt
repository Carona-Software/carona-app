package com.example.caronaapp.data_class

data class Usuario(
    var nome: String = "",
    var email: String = "",
    var cpf: String = "",
    var senha: String = "",
    var perfil: String = "",
    var genero: String = "",
    var dataNascimento: String = "",
//    var dataNascimento: Date = Date(),
    var foto: String = "",
    var endereco: Endereco? = null
)