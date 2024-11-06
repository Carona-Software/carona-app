package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.viagem.ViagemCriacaoDto
import com.example.caronaapp.data.dto.viagem.ViagemDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemListagemDto
import com.example.caronaapp.service.api.ViagemApi
import com.example.caronaapp.service.repository.ViagemRepository
import retrofit2.Response

class ViagemRepositoryImpl(private val viagemApi: ViagemApi) : ViagemRepository {
    override suspend fun save(viagem: ViagemCriacaoDto): Response<ViagemListagemDto> {
        return viagemApi.save(viagem)
    }

    override suspend fun findAll(): Response<List<ViagemListagemDto>> {
        return viagemApi.findAll()
    }

    override suspend fun findById(id: Int): Response<ViagemDetalhesListagemDto> {
        return viagemApi.findById(id)
    }

    override suspend fun findAllByUsuarioId(id: Int): Response<List<ViagemListagemDto>> {
        return viagemApi.findAllByUsuarioId(id)
    }

    override suspend fun delete(id: Int): Response<Void> {
        return viagemApi.delete(id)
    }
}