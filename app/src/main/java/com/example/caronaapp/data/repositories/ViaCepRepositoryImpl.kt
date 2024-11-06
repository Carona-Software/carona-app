package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.endereco.EnderecoCriacaoDto
import com.example.caronaapp.service.api.ViaCepApi
import com.example.caronaapp.service.repository.ViaCepRepository
import retrofit2.Response

class ViaCepRepositoryImpl(private val viaCepApi: ViaCepApi) : ViaCepRepository {
    override suspend fun getEndereco(cep: String): Response<EnderecoCriacaoDto> {
        return viaCepApi.getEndereco(cep)
    }
}