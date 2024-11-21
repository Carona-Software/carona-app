package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.viagem.PageViagemListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemCriacaoDto
import com.example.caronaapp.data.dto.viagem.ViagemDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemListagemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ViagemApi {

    @POST("viagens")
    suspend fun save(@Body viagem: ViagemCriacaoDto): Response<ViagemListagemDto>

    @GET("viagens")
    suspend fun findAll(
        @Query("pagina") pagina: Int = 0,
        @Query("itens") itens: Int = 10,
        @Query("pontoPartida.latitude") latitudePontoPartida: Double,
        @Query("pontoPartida.longitude") longitudePontoPartida: Double,
        @Query("pontoChegada.latitude") latitudePontoChegada: Double,
        @Query("pontoChegada.longitude") longitudePontoChegada: Double,
        @Query("data") data: String,
        @Query("capacidadePassageiros") capacidadePassageiros: Int?,
        @Query("precoMinimo") precoMinimo: Double?,
        @Query("precoMaximo") precoMaximo: Double?,
        @Query("apenasMulheres") apenasMulheres: Boolean?
    ): Response<PageViagemListagemDto>

    @GET("viagens/{id}")
    suspend fun findById(@Path("id") id: Int): Response<ViagemDetalhesListagemDto>

    @GET("viagens/usuario/{id}")
    suspend fun findAllByUsuarioId(@Path("id") id: Int): Response<List<ViagemListagemDto>>

    @DELETE("viagens/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Void>

    @PUT("viagens/{id}/inicio")
    suspend fun start(@Path("id") id: Int): Response<ViagemDetalhesListagemDto>

    @PUT("viagens/{id}/finalizacao")
    suspend fun finish(@Path("id") id: Int): Response<ViagemDetalhesListagemDto>
}