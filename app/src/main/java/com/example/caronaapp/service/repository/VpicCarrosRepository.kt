package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.vpic_carros.VpicCarrosMarcaResponse
import com.example.caronaapp.data.dto.vpic_carros.VpicCarrosModeloResponse
import retrofit2.Response

interface VpicCarrosRepository {

    suspend fun getMarcasCarro(): Response<VpicCarrosMarcaResponse>

    suspend fun getModelosCarroByMarca(marca: String): Response<VpicCarrosModeloResponse>
}