package com.example.caronaapp.data.repositories

import android.util.Log
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioLoginDto
import com.example.caronaapp.service.api.UsuarioApi
import com.example.caronaapp.service.repository.UsuarioRepository
import retrofit2.Response

class UsuarioRepositoryImpl(private val usuarioApi: UsuarioApi) : UsuarioRepository {
    override suspend fun post(usuarioCriacaoDto: UsuarioCriacaoDto): Response<UsuarioDetalhesListagemDto> {
        return usuarioApi.post(usuarioCriacaoDto)
    }

    override suspend fun findAll(): Response<List<UsuarioListagemDto>> {
        return usuarioApi.findAll()
    }

    override suspend fun login(usuario: UsuarioLoginDto): Response<UsuarioDetalhesListagemDto> {
        return usuarioApi.login(usuario)
    }

    override suspend fun findById(id: Int): Response<UsuarioDetalhesListagemDto> {
        return usuarioApi.findById(id)
    }

    override suspend fun delete(id: Int): Response<Void> {
        return usuarioApi.delete(id)
    }

    override suspend fun update(
        id: Int,
        usuario: UsuarioCriacaoDto
    ): Response<UsuarioDetalhesListagemDto> {
        return usuarioApi.update(id, usuario)
    }

    override suspend fun resetPassword(email: String): Response<String> {
        return usuarioApi.resetPassword(email)
    }
}