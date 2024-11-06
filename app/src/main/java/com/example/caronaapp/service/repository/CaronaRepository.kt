package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.carona.CaronaCriacaoDto
import com.example.caronaapp.data.dto.carona.CaronaListagemDto
import retrofit2.Response

interface CaronaRepository {

    suspend fun save(caronaCriacaoDto: CaronaCriacaoDto): Response<CaronaListagemDto>

    suspend fun findByUsuarioId(id: Int): Response<List<CaronaListagemDto>>

    suspend fun findByViagemId(id: Int): Response<List<CaronaListagemDto>>

    suspend fun delete(viagemId: Int, usuarioId: Int): Response<Void>

}