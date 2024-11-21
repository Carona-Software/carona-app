package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.viagem.PageViagemListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemCriacaoDto
import com.example.caronaapp.data.dto.viagem.ViagemDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemProcuraDto
import retrofit2.Response

interface ViagemRepository {

    suspend fun save(viagem: ViagemCriacaoDto): Response<ViagemListagemDto>

    suspend fun findAll(viagem: ViagemProcuraDto): Response<PageViagemListagemDto>

    suspend fun findById(id: Int): Response<ViagemDetalhesListagemDto>

    suspend fun findAllByUsuarioId(id: Int): Response<List<ViagemListagemDto>>

    suspend fun delete(id: Int): Response<Void>

    suspend fun start(id: Int): Response<ViagemDetalhesListagemDto>

    suspend fun finish(id: Int): Response<ViagemDetalhesListagemDto>
}