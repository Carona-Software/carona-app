package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.carro.CarroCriacaoDto
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import com.example.caronaapp.service.api.CarroApi
import com.example.caronaapp.service.repository.CarroRepository
import retrofit2.Response

class CarroRepositoryImpl(private val carroApi: CarroApi) : CarroRepository {
    override suspend fun save(carroCriacaoDto: CarroCriacaoDto): Response<CarroListagemDto> {
        return carroApi.save(carroCriacaoDto)
    }

    override suspend fun findAll(): Response<List<CarroListagemDto>> {
        return carroApi.findAll()
    }

    override suspend fun findByUsuarioId(id: Int): Response<List<CarroListagemDto>> {
        return carroApi.findByUsuarioId(id)
    }

    override suspend fun edit(id: Int, carro: CarroCriacaoDto): Response<CarroListagemDto> {
        return carroApi.edit(id, carro)
    }

    override suspend fun delete(id: Int): Response<Void> {
        return carroApi.delete(id)
    }

}