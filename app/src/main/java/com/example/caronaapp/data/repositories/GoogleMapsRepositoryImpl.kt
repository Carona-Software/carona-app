package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.google_maps.GeocodeResponse
import com.example.caronaapp.service.api.GoogleMapsApi
import com.example.caronaapp.service.repository.GoogleMapsRepository
import retrofit2.Response

class GoogleMapsRepositoryImpl(private val googleMapsApi: GoogleMapsApi) : GoogleMapsRepository {
    private val API_KEY = "AIzaSyBCgrMgCudI7Jcc3xd8DDZAlqb8_7lWvF4"

    override suspend fun getGeocode(address: String): Response<GeocodeResponse> {
        return googleMapsApi.getGeocode(address, API_KEY)
    }
}