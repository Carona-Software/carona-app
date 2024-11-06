package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.google_maps.GeocodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsApi {

    @GET("geocode/json")
    suspend fun getGeocode(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Response<GeocodeResponse>
}