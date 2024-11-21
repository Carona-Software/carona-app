package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemCriacaoDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemListagemDto
import com.example.caronaapp.service.api.SolicitacaoViagemApi
import com.example.caronaapp.service.repository.SolicitacaoViagemRepository
import retrofit2.Response

class SolicitacaoViagemRepositoryImpl(private val solicitacaoApi: SolicitacaoViagemApi) :
    SolicitacaoViagemRepository {
    override suspend fun save(solicitacao: SolicitacaoViagemCriacaoDto): Response<SolicitacaoViagemListagemDto> {
        return solicitacaoApi.save(solicitacao)
    }

    override suspend fun findPendentesByViagemId(id: Int): Response<List<SolicitacaoViagemListagemDto>> {
        return solicitacaoApi.findPendentesByViagemId(id)
    }

    override suspend fun approve(id: Int): Response<SolicitacaoViagemListagemDto> {
        return solicitacaoApi.approve(id)
    }

    override suspend fun refuse(id: Int): Response<SolicitacaoViagemListagemDto> {
        return solicitacaoApi.refuse(id)
    }

    override suspend fun delete(id: Int): Response<Void> {
        return solicitacaoApi.delete(id)
    }
}