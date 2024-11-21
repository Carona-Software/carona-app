package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.fidelizacao.FidelizacaoCriacaoDto
import com.example.caronaapp.data.dto.fidelizacao.FidelizacaoListagemDto
import com.example.caronaapp.data.dto.usuario.FidelizadoListagemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FidelizacaoApi {

    @POST("fidelizacoes")
    suspend fun save(@Body fidelizacao: FidelizacaoCriacaoDto): Response<FidelizacaoListagemDto>

    @GET("fidelizacoes/usuario/{id}")
    suspend fun findByUsuarioId(@Path("id") id: Int): Response<List<FidelizadoListagemDto>>

    @DELETE("fidelizacoes/{motoristaId}/{passageiroId}")
    suspend fun delete(
        @Path("motoristaId") motoristaId: Int,
        @Path("passageiroId") passageiroId: Int
    ): Response<Void>

    @GET("fidelizacoes/{passageiroId}/{motoristaId}")
    suspend fun existsFidelizacao(
        @Path("passageiroId") passageiroId: Int,
        @Path("motoristaId") motoristaId: Int
    ): Response<Boolean>

}