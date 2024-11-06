package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.google_maps.GeocodeResponse
import retrofit2.Response

interface GoogleMapsRepository {

    suspend fun getGeocode(address: String, apiKey: String): Response<GeocodeResponse>
}