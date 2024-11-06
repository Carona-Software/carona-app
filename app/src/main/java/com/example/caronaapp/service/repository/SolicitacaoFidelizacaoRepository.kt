package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.solicitacao.SolicitacaoFidelizacaoCriacaoDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoFidelizacaoListagemDto
import retrofit2.Response

interface SolicitacaoFidelizacaoRepository {

    suspend fun save(solicitacao: SolicitacaoFidelizacaoCriacaoDto): Response<SolicitacaoFidelizacaoListagemDto>

    suspend fun findPendentesByUsuarioId(id: Int): Response<List<SolicitacaoFidelizacaoListagemDto>>

    suspend fun approve(id: Int): Response<SolicitacaoFidelizacaoListagemDto>

    suspend fun refuse(id: Int): Response<SolicitacaoFidelizacaoListagemDto>
}