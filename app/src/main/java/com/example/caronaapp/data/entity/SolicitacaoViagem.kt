package com.example.caronaapp.data.entity

import com.example.caronaapp.data.enums.StatusSolicitacao

data class SolicitacaoViagem(
    val id: Int,
    val usuario: Usuario,
    val viagem: Viagem,
    val status: StatusSolicitacao
)
