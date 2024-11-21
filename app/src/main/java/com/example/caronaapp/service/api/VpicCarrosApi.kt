package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.vpic_carros.VpicCarrosMarcaResponse
import com.example.caronaapp.data.dto.vpic_carros.VpicCarrosModeloResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VpicCarrosApi {

    @GET("GetMakesForVehicleType/car?format=json")
    suspend fun getMarcasCarro() : Response<VpicCarrosMarcaResponse>

    @GET("GetModelsForMake/{marca}?format=json")
    suspend fun getModelosCarroByMarca(@Path("marca") marca: String) : Response<VpicCarrosModeloResponse>
}