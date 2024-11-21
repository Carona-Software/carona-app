package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.mapbox.MapboxResponseDto
import com.example.caronaapp.service.api.MapboxApi
import com.example.caronaapp.service.repository.MapboxRepository
import retrofit2.Response

class MapboxRepositoryImpl(private val mapboxApi: MapboxApi) : MapboxRepository {
    private val ACCESS_TOKEN =
        "pk.eyJ1IjoiZ3VzdGF2b21lZHMiLCJhIjoiY2x4aHZwMW1pMWk2aTJtcHJtcTk1cW1sciJ9.1hI8OLMYq8hS8Dcq5wGYNA"

    override suspend fun getDistanceBetweenPlaces(
        latitudePartida: Double,
        longitudePartida: Double,
        latitudeDestino: Double,
        longitudeDestino: Double
    ): Response<MapboxResponseDto> {
        return mapboxApi.getDistanceBetweenPlaces(
            latitudePartida,
            longitudePartida,
            latitudeDestino,
            longitudeDestino,
            ACCESS_TOKEN
        )
    }
}