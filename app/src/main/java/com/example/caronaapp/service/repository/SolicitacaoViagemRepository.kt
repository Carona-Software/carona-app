package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemCriacaoDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemListagemDto
import retrofit2.Response

interface SolicitacaoViagemRepository {

    suspend fun save(solicitacao: SolicitacaoViagemCriacaoDto): Response<SolicitacaoViagemListagemDto>

    suspend fun findPendentesByViagemId(id: Int): Response<List<SolicitacaoViagemListagemDto>>

    suspend fun approve(id: Int): Response<SolicitacaoViagemListagemDto>

    suspend fun refuse(id: Int): Response<SolicitacaoViagemListagemDto>

    suspend fun delete(id: Int): Response<Void>
}