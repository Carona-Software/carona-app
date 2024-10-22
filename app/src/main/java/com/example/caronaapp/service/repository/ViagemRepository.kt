package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.viagem.ViagemCriacaoDto
import com.example.caronaapp.data.dto.viagem.ViagemDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemListagemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ViagemRepository {

    @POST("viagens")
    suspend fun save(@Body viagem: ViagemCriacaoDto): Response<ViagemListagemDto>

    @GET("viagens")
    suspend fun findAll(): Response<List<ViagemListagemDto>>

    @GET("viagens/{id}")
    suspend fun findById(@Path("id") id: Int): Response<ViagemDetalhesListagemDto>

    @GET("viagens/usuario/{id}")
    suspend fun findAllByUsuarioId(@Path("id") id: Int): Response<List<ViagemListagemDto>>

    @DELETE("viagens/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Void>
}