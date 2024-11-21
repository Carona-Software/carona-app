package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.carona.CaronaCriacaoDto
import com.example.caronaapp.data.dto.carona.CaronaListagemDto
import com.example.caronaapp.service.api.CaronaApi
import com.example.caronaapp.service.repository.CaronaRepository
import retrofit2.Response

class CaronaRepositoryImpl(private val caronaApi: CaronaApi) : CaronaRepository {

    override suspend fun save(caronaCriacaoDto: CaronaCriacaoDto): Response<CaronaListagemDto> {
        return caronaApi.save(caronaCriacaoDto)
    }

    override suspend fun findByUsuarioId(id: Int): Response<List<CaronaListagemDto>> {
        return caronaApi.findByUsuarioId(id)
    }

    override suspend fun findByViagemId(id: Int): Response<List<CaronaListagemDto>> {
        return caronaApi.findByViagemId(id)
    }

    override suspend fun delete(viagemId: Int, usuarioId: Int): Response<Void> {
        return caronaApi.delete(viagemId, usuarioId)
    }

    override suspend fun countViagensBetweenMotoristaAndPassageiro(
        motoristaId: Int,
        passageiroId: Int
    ): Response<Int> {
        return caronaApi.countViagensBetweenMotoristaAndPassageiro(motoristaId, passageiroId)
    }
}