package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.fidelizacao.FidelizacaoCriacaoDto
import com.example.caronaapp.data.dto.fidelizacao.FidelizacaoListagemDto
import com.example.caronaapp.data.dto.usuario.FidelizadoListagemDto
import com.example.caronaapp.service.api.FidelizacaoApi
import com.example.caronaapp.service.repository.FidelizacaoRepository
import retrofit2.Response

class FidelizacaoRepositoryImpl(private val fidelizacaoApi: FidelizacaoApi) :
    FidelizacaoRepository {
    override suspend fun save(fidelizacao: FidelizacaoCriacaoDto): Response<FidelizacaoListagemDto> {
        return fidelizacaoApi.save(fidelizacao)
    }

    override suspend fun findByUsuarioId(id: Int): Response<List<FidelizadoListagemDto>> {
        return fidelizacaoApi.findByUsuarioId(id)
    }

    override suspend fun delete(motoristaId: Int, passageiroId: Int): Response<Void> {
        return fidelizacaoApi.delete(motoristaId, passageiroId)
    }

    override suspend fun existsFidelizacao(passageiroId: Int, motoristaId: Int): Response<Boolean> {
        return fidelizacaoApi.existsFidelizacao(passageiroId, motoristaId)
    }
}