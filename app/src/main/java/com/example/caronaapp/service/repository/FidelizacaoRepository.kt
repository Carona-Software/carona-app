package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.fidelizacao.FidelizacaoCriacaoDto
import com.example.caronaapp.data.dto.fidelizacao.FidelizacaoListagemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FidelizacaoRepository {

    @POST("fidelizacoes")
    suspend fun save(@Body fidelizacao: FidelizacaoCriacaoDto): Response<FidelizacaoListagemDto>

    @GET("fidelizacoes/usuario/{id}")
    suspend fun findByUsuarioId(@Path("id") id: Int): Response<List<FidelizacaoListagemDto>>

    @DELETE("fidelizacoes/{motoristaId}/{passageiroId}")
    suspend fun delete(
        @Path("motoristaId") motoristaId: Int,
        @Path("passageiroId") passageiroId: Int
    )

}