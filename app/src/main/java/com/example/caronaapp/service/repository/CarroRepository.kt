package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.carro.CarroCriacaoDto
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CarroRepository {

    @POST("carros")
    suspend fun save(@Body carroCriacaoDto: CarroCriacaoDto): Response<CarroListagemDto>

    @GET("carros")
    suspend fun findAll(): Response<List<CarroListagemDto>>

    @GET("carros/usuario/{id}")
    suspend fun findByUsuarioId(@Path("id") id: Int): Response<List<CarroListagemDto>>

    @PUT("carros/{id}")
    suspend fun edit(@Path("id") id: Int, @Body carro: CarroCriacaoDto): Response<CarroListagemDto>

    @DELETE("carros/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Void>
}