package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.endereco.EnderecoCriacaoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepRepository {

    @GET("{cep}/json/")
    suspend fun getEndereco(@Path("cep") cep: String): Response<EnderecoCriacaoDto>
}