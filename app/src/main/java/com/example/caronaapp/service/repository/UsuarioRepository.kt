package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioLoginDto
import retrofit2.Response

interface UsuarioRepository {

    suspend fun post(usuarioCriacaoDto: UsuarioCriacaoDto): Response<UsuarioDetalhesListagemDto>

    suspend fun findAll(): Response<List<UsuarioListagemDto>>

    suspend fun login(usuario: UsuarioLoginDto): Response<UsuarioDetalhesListagemDto>

    suspend fun findById(id: Int): Response<UsuarioDetalhesListagemDto>

    suspend fun delete(id: Int): Response<Void>

    suspend fun update(id: Int, usuario: UsuarioCriacaoDto): Response<UsuarioDetalhesListagemDto>

    suspend fun resetPassword(email: String): Response<String>
}