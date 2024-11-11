package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.viagem.ViagemCriacaoDto
import com.example.caronaapp.data.dto.viagem.ViagemDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemListagemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface ViagemApi {

    @POST("viagens")
    suspend fun save(@Body viagem: ViagemCriacaoDto): Response<ViagemListagemDto>

    @GET("viagens")
    suspend fun findAll(
        @Query("pontoPartida.latitude") latitudePontoPartida: Double,
        @Query("pontoPartida.longitude") longitudePontoPartida: Double,
        @Query("pontoChegada.longitude") latitudePontoChegada: Double,
        @Query("pontoChegada.longitude") longitudePontoChegada: Double,
        @Query("data") data: LocalDate,
        @Query("capacidadePassageiros") capacidadePassageiros: Int,
        @Query("precoMinimo") precoMinimo: Double,
        @Query("precoMaximo") precoMaximo: Double,
        @Query("apenasMulheres") apenasMulheres: Boolean
    ): Response<List<ViagemListagemDto>>

    @GET("viagens/{id}")
    suspend fun findById(@Path("id") id: Int): Response<ViagemDetalhesListagemDto>

    @GET("viagens/usuario/{id}")
    suspend fun findAllByUsuarioId(@Path("id") id: Int): Response<List<ViagemListagemDto>>

    @DELETE("viagens/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Void>
}