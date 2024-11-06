package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.carro.CarroCriacaoDto
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import retrofit2.Response

interface CarroRepository {

    suspend fun save(carroCriacaoDto: CarroCriacaoDto): Response<CarroListagemDto>

    suspend fun findAll(): Response<List<CarroListagemDto>>

    suspend fun findByUsuarioId(id: Int): Response<List<CarroListagemDto>>

    suspend fun edit(id: Int, carro: CarroCriacaoDto): Response<CarroListagemDto>

    suspend fun delete(id: Int): Response<Void>
}