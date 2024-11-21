package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.mapbox.MapboxResponseDto
import retrofit2.Response

interface MapboxRepository {

    suspend fun getDistanceBetweenPlaces(
        latitudePartida: Double,
        longitudePartida: Double,
        latitudeDestino: Double,
        longitudeDestino: Double
    ): Response<MapboxResponseDto>
}