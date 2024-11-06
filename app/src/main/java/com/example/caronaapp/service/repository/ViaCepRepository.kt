package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.endereco.EnderecoCriacaoDto
import retrofit2.Response

interface ViaCepRepository {

    suspend fun getEndereco(cep: String): Response<EnderecoCriacaoDto>
}