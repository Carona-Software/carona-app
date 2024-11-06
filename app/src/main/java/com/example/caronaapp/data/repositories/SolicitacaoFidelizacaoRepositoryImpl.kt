package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.solicitacao.SolicitacaoFidelizacaoCriacaoDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoFidelizacaoListagemDto
import com.example.caronaapp.service.api.SolicitacaoFidelizacaoApi
import com.example.caronaapp.service.repository.SolicitacaoFidelizacaoRepository
import retrofit2.Response

class SolicitacaoFidelizacaoRepositoryImpl(private val solicitacaoApi: SolicitacaoFidelizacaoApi) :
    SolicitacaoFidelizacaoRepository {
    override suspend fun save(solicitacao: SolicitacaoFidelizacaoCriacaoDto): Response<SolicitacaoFidelizacaoListagemDto> {
        return solicitacaoApi.save(solicitacao)
    }

    override suspend fun findPendentesByUsuarioId(id: Int): Response<List<SolicitacaoFidelizacaoListagemDto>> {
        return solicitacaoApi.findPendentesByUsuarioId(id)
    }

    override suspend fun approve(id: Int): Response<SolicitacaoFidelizacaoListagemDto> {
        return solicitacaoApi.approve(id)
    }

    override suspend fun refuse(id: Int): Response<SolicitacaoFidelizacaoListagemDto> {
        return solicitacaoApi.refuse(id)
    }
}