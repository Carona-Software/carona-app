package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioLoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsuarioApi {

    @POST("usuarios")
    suspend fun post(@Body usuarioCriacaoDto: UsuarioCriacaoDto): Response<UsuarioDetalhesListagemDto>

    @GET("usuarios")
    suspend fun findAll(): Response<List<UsuarioListagemDto>>

    @POST("usuarios/login")
    suspend fun login(@Body usuario: UsuarioLoginDto): Response<UsuarioDetalhesListagemDto>

    @GET("usuarios/{id}")
    suspend fun findById(@Path("id") id: Int): Response<UsuarioDetalhesListagemDto>

    @DELETE("usuarios/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Void>

    @PUT("usuarios/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body usuario: UsuarioCriacaoDto
    ): Response<UsuarioDetalhesListagemDto>

    @POST("usuarios/redefinir-senha")
    suspend fun resetPassword(@Query("email") email: String): Response<String>
}