package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.vpic_carros.VpicCarrosMarcaResponse
import com.example.caronaapp.data.dto.vpic_carros.VpicCarrosModeloResponse
import com.example.caronaapp.service.api.VpicCarrosApi
import com.example.caronaapp.service.repository.VpicCarrosRepository
import retrofit2.Response

class VpicCarrosRepositoryImpl(
    private val vpicCarrosApi: VpicCarrosApi
) : VpicCarrosRepository {
    override suspend fun getMarcasCarro(): Response<VpicCarrosMarcaResponse> {
        return vpicCarrosApi.getMarcasCarro()
    }

    override suspend fun getModelosCarroByMarca(marca: String): Response<VpicCarrosModeloResponse> {
        return vpicCarrosApi.getModelosCarroByMarca(marca)
    }
}