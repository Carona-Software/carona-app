package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.google_maps.GeocodeResponse
import com.example.caronaapp.service.api.GoogleMapsApi
import com.example.caronaapp.service.repository.GoogleMapsRepository
import retrofit2.Response

class GoogleMapsRepositoryImpl(private val googleMapsApi: GoogleMapsApi) : GoogleMapsRepository {
    override suspend fun getGeocode(address: String, apiKey: String): Response<GeocodeResponse> {
        return googleMapsApi.getGeocode(address, apiKey)
    }
}