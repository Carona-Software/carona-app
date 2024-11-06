package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.fidelizacao.FidelizacaoCriacaoDto
import com.example.caronaapp.data.dto.fidelizacao.FidelizacaoListagemDto
import com.example.caronaapp.data.dto.usuario.FidelizadoListagemDto
import retrofit2.Response

interface FidelizacaoRepository {

    suspend fun save(fidelizacao: FidelizacaoCriacaoDto): Response<FidelizacaoListagemDto>

    suspend fun findByUsuarioId(id: Int): Response<List<FidelizadoListagemDto>>

    suspend fun delete(motoristaId: Int, passageiroId: Int): Response<Void>
}