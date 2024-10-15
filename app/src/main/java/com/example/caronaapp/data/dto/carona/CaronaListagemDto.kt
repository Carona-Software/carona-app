package com.example.caronaapp.data.dto.carona

import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemSimplesListagemDto

data class CaronaListagemDto(
    val usuario: UsuarioSimplesListagemDto,
    val viagem: ViagemSimplesListagemDto
)
