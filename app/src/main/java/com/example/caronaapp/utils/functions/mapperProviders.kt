package com.example.caronaapp.utils.functions

import com.example.caronaapp.data.dto.usuario.PassageiroDto
import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto

fun toPassageiroDto(usuario: UsuarioSimplesListagemDto): PassageiroDto {
    return PassageiroDto(
        id = usuario.id,
        nome = usuario.nome,
        perfil = usuario.perfil,
        fotoUrl = usuario.fotoUrl,
        notaGeral = usuario.notaGeral,
        foiAvaliado = false,
        isFotoValida = usuario.isFotoValida
    )
}