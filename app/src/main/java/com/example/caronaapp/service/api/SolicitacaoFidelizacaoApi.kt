package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.solicitacao.SolicitacaoFidelizacaoCriacaoDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoFidelizacaoListagemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SolicitacaoFidelizacaoApi {

    @POST("solicitacoes-fidelizacao")
    suspend fun save(@Body solicitacao: SolicitacaoFidelizacaoCriacaoDto): Response<SolicitacaoFidelizacaoListagemDto>

    @GET("solicitacoes-fidelizacao/usuario/{id}")
    suspend fun findPendentesByUsuarioId(@Path("id") id: Int): Response<List<SolicitacaoFidelizacaoListagemDto>>

    @PUT("solicitacoes-fidelizacao/{id}/aprovacao")
    suspend fun approve(@Path("id") id: Int): Response<SolicitacaoFidelizacaoListagemDto>

    @PUT("solicitacoes-fidelizacao/{id}/recusa")
    suspend fun refuse(@Path("id") id: Int): Response<SolicitacaoFidelizacaoListagemDto>
}