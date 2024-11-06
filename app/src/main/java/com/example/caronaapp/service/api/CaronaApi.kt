package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.carona.CaronaCriacaoDto
import com.example.caronaapp.data.dto.carona.CaronaListagemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CaronaApi {

    @POST("caronas")
    suspend fun save(@Body caronaCriacaoDto: CaronaCriacaoDto): Response<CaronaListagemDto>

    @GET("caronas/usuario/{id}")
    suspend fun findByUsuarioId(@Path("id") id: Int): Response<List<CaronaListagemDto>>

    @GET("caronas/viagem/{id}")
    suspend fun findByViagemId(@Path("id") id: Int): Response<List<CaronaListagemDto>>

    @DELETE("caronas/{viagemId}/{usuarioId}")
    suspend fun delete(
        @Path("viagemId") viagemId: Int,
        @Path("usuarioId") usuarioId: Int
    ): Response<Void>
}