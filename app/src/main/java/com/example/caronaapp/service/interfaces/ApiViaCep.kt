package com.example.caronaapp.service.interfaces

import com.example.caronaapp.data.Endereco
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiViaCep {
    @GET("{cep}/json/")
    suspend fun getEndereco(@Path("cep") cep: String): Response<Endereco>
}