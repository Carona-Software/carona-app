package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.viagem.PageViagemListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemCriacaoDto
import com.example.caronaapp.data.dto.viagem.ViagemDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemProcuraDto
import com.example.caronaapp.service.api.ViagemApi
import com.example.caronaapp.service.repository.ViagemRepository
import retrofit2.Response

class ViagemRepositoryImpl(private val viagemApi: ViagemApi) : ViagemRepository {
    override suspend fun save(viagem: ViagemCriacaoDto): Response<ViagemListagemDto> {
        return viagemApi.save(viagem)
    }

    override suspend fun findAll(viagem: ViagemProcuraDto): Response<PageViagemListagemDto> {
        return viagemApi.findAll(
            latitudePontoPartida = viagem.pontoPartida!!.latitude,
            longitudePontoPartida = viagem.pontoPartida.longitude,
            latitudePontoChegada = viagem.pontoChegada!!.latitude,
            longitudePontoChegada = viagem.pontoChegada.longitude,
            data = viagem.data,
            capacidadePassageiros = viagem.capacidadePassageiros,
            precoMinimo = viagem.precoMinimo,
            precoMaximo = viagem.precoMaximo,
            apenasMulheres = viagem.apenasMulheres
        )
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

    override suspend fun start(id: Int): Response<ViagemDetalhesListagemDto> {
        return viagemApi.start(id)
    }

    override suspend fun finish(id: Int): Response<ViagemDetalhesListagemDto> {
        return viagemApi.finish(id)
    }
}