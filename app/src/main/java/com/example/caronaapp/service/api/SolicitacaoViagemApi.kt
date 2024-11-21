package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemCriacaoDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemListagemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SolicitacaoViagemApi {

    @POST("solicitacoes-viagem")
    suspend fun save(@Body solicitacao: SolicitacaoViagemCriacaoDto): Response<SolicitacaoViagemListagemDto>

    @GET("solicitacoes-viagem/viagem/{id}")
    suspend fun findPendentesByViagemId(@Path("id") id: Int): Response<List<SolicitacaoViagemListagemDto>>

    @PUT("solicitacoes-viagem/{id}/aprovacao")
    suspend fun approve(@Path("id") id: Int): Response<SolicitacaoViagemListagemDto>

    @PUT("solicitacoes-viagem/{id}/recusa")
    suspend fun refuse(@Path("id") id: Int): Response<SolicitacaoViagemListagemDto>

    @DELETE("solicitacoes-viagem/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Void>
}