package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.mapbox.MapboxResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapboxApi {

    @GET("{longitudePartida},{latitudePartida};{longitudeDestino},{latitudeDestino}?steps=false&geometries=geojson&language=pt")
    suspend fun getDistanceBetweenPlaces(
        @Path("latitudePartida") latitudePartida: Double,
        @Path("longitudePartida") longitudePartida: Double,
        @Path("latitudeDestino") latitudeDestino: Double,
        @Path("longitudeDestino") longitudeDestino: Double,
        @Query("access_token") token: String,
    ): Response<MapboxResponseDto>
}